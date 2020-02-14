package com.example.notepad.main.mvi

import com.example.notepad.main.utils.ReplaceFragmentArguments
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface MainView : MvpView {
    val replaceIntent: Observable<ReplaceFragmentArguments>

    fun render(viewState: MainViewState)
}