package com.example.notepad.notesList.mvi

import com.example.notepad.db.models.Note
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface NotesListView : MvpView {
    val searchIntent: Observable<String>
    val nextPageIntent: Observable<Pair<Int, String>>
    val deleteIntent: Observable<Note>
    val addIntent: Observable<Unit>

    fun render(state: NotesListViewState)
}