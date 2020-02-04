package com.example.notepad.notesList.mvi

import android.content.Context
import com.example.notepad.db.Repository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.services.NotesListUseCase
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter

class NotesListPresenter(context: Context) :
    MviBasePresenter<NotesListView, NotesListViewState>() {

    private val reducer by lazy { NotesListViewReducer() }
    private val repository by lazy { Repository<Note>(context) }
    private val useCase by lazy { NotesListUseCase() }

    override fun bindIntents() {
        val allNotes = repository.getItemsList(Repository.allNotes)

        val searchIntent = intent { it.searchIntent }
            .switchMap { useCase.searchNotes(it, allNotes) }
            .map { NotesListViewStateChange.NotesListChanged(it) }
            .scan(NotesListViewState()) { state: NotesListViewState, change: NotesListViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(searchIntent) { view, viewState -> view.render(viewState) }
    }
}