package com.example.notepad.notesList.mvi

import android.content.Context
import com.example.notepad.db.NoteRepository
import com.example.notepad.notesList.services.NotesListUseCase
import com.example.notepad.notesList.utils.NotesListSearchResult
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotesListPresenter(context: Context) :
    MviBasePresenter<NotesListView, NotesListViewState>() {

    private val reducer by lazy { NotesListViewReducer() }
    private val repository by lazy { NoteRepository.getInstance(context) }
    private val useCase by lazy { NotesListUseCase() }

    override fun bindIntents() {
        val searchIntent = intent { it.searchIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.searchNotes(it, repository)
                    .startWith(NotesListSearchResult.Pending)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NotesListViewStateChange.NotesListChanged(it) }
            .scan(NotesListViewState()) { state: NotesListViewState, change: NotesListViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(searchIntent) { view, viewState -> view.render(viewState) }
    }
}