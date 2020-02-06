package com.example.notepad.notesList.mvi

import com.example.notepad.db.models.Note
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface NotesListView : MvpView {
    val searchIntent: Observable<String>
    val nextPageIntent: Observable<Int>
    val deleteIntent: Observable<Note>

    fun render(state: NotesListViewState)
}