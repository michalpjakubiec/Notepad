package com.example.notepad.main.mvi

import android.content.Context
import com.example.notepad.main.services.MainUseCase
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(context: Context) : MviBasePresenter<MainView, MainViewState>() {
    private val reducer by lazy { MainReducer() }
    private val useCase by lazy { MainUseCase(context) }

    override fun bindIntents() {
        val redirectIntent = intent { it.replaceIntent }
            .observeOn(Schedulers.io())
            .switchMap {
                useCase.getFragment(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { MainViewStateChange.FragmentChanged(it) }

        val stream = redirectIntent
            .scan(MainViewState()) { state: MainViewState, change: MainViewStateChange ->
                return@scan reducer.reduce(state, change)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }
    }
}