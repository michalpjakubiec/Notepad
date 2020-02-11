package com.example.notepad.note.mvi

import android.content.Context
import com.example.notepad.note.services.NoteUseCase
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotePresenter(context: Context) : MviBasePresenter<NoteView, NoteViewState>() {

    private val reducer by lazy { NoteReducer() }
    private val useCase by lazy { NoteUseCase(context) }

    override fun bindIntents() {
        val validationIntent = intent { it.validationIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.validateNote(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NoteViewStateChange.ValidationChange(it) }

        val saveIntent = intent { it.saveIntent }
            .subscribeOn(Schedulers.io())
            .switchMap {
                useCase.saveNote(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NoteViewStateChange.SaveChange(it) }

        val favouriteIntent = intent { it.favouriteIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.changeFavouriteStatus(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { NoteViewStateChange.FavouriteChange(it) }


        val stream = Observable
            .merge(validationIntent, saveIntent, favouriteIntent)
            .scan(NoteViewState()) { state: NoteViewState, change: NoteViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }
    }
}