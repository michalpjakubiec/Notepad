package com.example.notepad.notesList.mvi

import com.example.notepad.notesList.helpers.NotesListUseCase
import com.example.notepad.notesList.utils.NotesListOperationResult
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NotesListPresenter(
    private val reducer: NotesListReducer,
    private val useCase: NotesListUseCase
) :
    MviBasePresenter<NotesListView, NotesListViewState>() {

    override fun bindIntents() {
        val searchIntent = intent { it.searchIntent }
            .debounce(2, TimeUnit.SECONDS)
            .switchMap {
                useCase.loadNextPage(it)
                    .delay(1, TimeUnit.SECONDS)
                    .startWith(NotesListOperationResult.Pending)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.DataSetChanged(it) }

        val nextPageIntent = intent { it.nextPageIntent }
            .switchMap {
                useCase.loadNextPage(it.first, it.second)
                    .delay(1, TimeUnit.SECONDS)
                    .startWith(NotesListOperationResult.Pending)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.PageChanged(it) }

        val deleteNoteIntent = intent { it.deleteIntent }
            .switchMap {
                useCase.deleteNote(it)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.NoteDeleted(it) }

        val addNoteIntent = intent { it.showNoteIntent }
            .switchMap {
                useCase.showNote(it)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.noteShowed(it) }

        val updateNoteIntent = intent { it.updateIntent }
            .switchMap {
                useCase.updateNote(it)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.NoteUpdated(it) }

        val initialLoadIntent = intent { it.initialLoadIntent }
            .switchMap {
                useCase.loadNextPage()
                    .delay(1, TimeUnit.SECONDS)
                    .startWith(NotesListOperationResult.Pending)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.DataSetChanged(it) }

        val refreshIntent = intent { it.refreshIntent }
            .switchMap {
                useCase.loadNextPage()
                    .delay(1, TimeUnit.SECONDS)
                    .startWith(NotesListOperationResult.Pending)
            }
            .observeOn(Schedulers.io())
            .map { NotesListViewStateChange.DataSetChanged(it) }

        val stream = Observable
            .merge(searchIntent, nextPageIntent, deleteNoteIntent, addNoteIntent)
            .mergeWith(updateNoteIntent)
            .mergeWith(initialLoadIntent)
            .mergeWith(refreshIntent)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(NotesListViewState()) { state: NotesListViewState, change: NotesListViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }
    }
}