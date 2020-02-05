package com.example.notepad.notesList.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface NotesListView : MvpView {
    val searchIntent: Observable<String>

    fun render(state: NotesListViewState)
}