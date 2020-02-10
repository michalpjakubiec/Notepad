package com.example.notepad.notesList.mvi

import android.content.Context
import com.example.notepad.notesList.services.NotesListUseCase
import com.example.notepad.notesList.utils.NotesListOperationResult
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NotesListPresenter(context: Context) :
    MviBasePresenter<NotesListView, NotesListViewState>() {

    private val reducer by lazy { NotesListViewReducer() }
    private val useCase by lazy { NotesListUseCase(context) }

    override fun bindIntents() {
        val searchIntent = intent { it.searchIntent }
            .subscribeOn(Schedulers.io())
            .debounce(2, TimeUnit.SECONDS)
            .switchMap {
                useCase.loadNextPage(it)
                    .startWith(NotesListOperationResult.Pending)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.PageChanged(it) }

        val nextPageIntent = intent { it.nextPageIntent }
            .subscribeOn(Schedulers.io())
            .switchMap {
                useCase.loadNextPage(it.first, it.second)
                    .startWith(NotesListOperationResult.Pending)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.PageChanged(it) }

        val deleteNoteIntent = intent { it.deleteIntent }
            .subscribeOn(Schedulers.io())
            .switchMap {
                useCase.deleteNote(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.NoteDeleted(it) }


        val addNoteIntent = intent { it.addIntent }
            .switchMap {
                useCase.addNote()
            }
            .map { NotesListViewStateChange.NoteAdded(it) }

        val updateNoteIntent = intent { it.updateIntent }
            .subscribeOn(Schedulers.io())
            .switchMap {
                useCase.updateNote(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.NoteUpdated(it) }

        val initialLoadIntent = intent { it.initialLoadIntent }
            .subscribeOn(Schedulers.io())
            .switchMap {
                useCase.loadNextPage(0)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.PageChanged(it) }

        val stream = Observable
            .merge(searchIntent, nextPageIntent, deleteNoteIntent, addNoteIntent)
            .mergeWith(updateNoteIntent)
            .mergeWith(initialLoadIntent)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(NotesListViewState()) { state: NotesListViewState, change: NotesListViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }
    }
}