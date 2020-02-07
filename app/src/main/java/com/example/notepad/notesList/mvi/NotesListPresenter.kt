package com.example.notepad.notesList.mvi

import android.content.Context
import com.example.notepad.db.NoteDatabase
import com.example.notepad.notesList.services.NotesListUseCase
import com.example.notepad.notesList.utils.NotesListNextPageResult
import com.example.notepad.notesList.utils.NotesListSearchResult
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NotesListPresenter(context: Context) :
    MviBasePresenter<NotesListView, NotesListViewState>() {

    private val reducer by lazy { NotesListViewReducer() }
    private val db by lazy { NoteDatabase.get(context) }
    private val useCase by lazy { NotesListUseCase() }

    override fun bindIntents() {
        val searchIntent = intent { it.searchIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.searchNotes(it, db)
                    .delay(2, TimeUnit.SECONDS)
                    .startWith(NotesListSearchResult.Pending)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.NotesListChanged(it) }

        val nextPageIntent = intent { it.nextPageIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.loadNextPage(it, db)
                    .delay(2, TimeUnit.SECONDS)
                    .startWith(NotesListNextPageResult.Pending)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.NotesPageChanged(it) }

        val deleteNoteIntent = intent { it.deleteIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.deleteNote(it, db)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.NoteDeleted(it) }

        val addNoteIntent = intent { it.addIntent }
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap {
                useCase.addNote()
            }
            .map { NotesListViewStateChange.NoteAdd(it) }

        val stream = Observable
            .merge(searchIntent, nextPageIntent, deleteNoteIntent,addNoteIntent)
            .scan(NotesListViewState()) { state: NotesListViewState, change: NotesListViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }
    }
}