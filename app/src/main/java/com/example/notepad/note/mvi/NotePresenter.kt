package com.example.notepad.note.mvi

import com.example.notepad.note.helpers.NoteUseCase
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotePresenter(
    private val reducer: NoteReducer,
    private val useCase: NoteUseCase
) : MviBasePresenter<NoteView, NoteViewState>() {

    override fun bindIntents() {
        val saveIntent = intent { it.saveIntent }
            .subscribeOn(Schedulers.io())
            .switchMap {
                useCase.saveNote(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NoteViewStateChange.SaveChange(it) }

        val loadIntent = intent { it.loadIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.getNote(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NoteViewStateChange.NoteChange(it) }

        val updateIntent = intent { it.updateIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.updateNoteDetails(it)
            }.observeOn(AndroidSchedulers.mainThread())
            .map { NoteViewStateChange.NoteDetailsChanged(it) }


        val stream = Observable
            .merge(saveIntent, updateIntent, loadIntent)
            .scan(NoteViewState()) { state: NoteViewState, change: NoteViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }
    }
}