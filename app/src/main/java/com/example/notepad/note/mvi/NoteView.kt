package com.example.notepad.note.mvi

import com.example.notepad.db.models.Note
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface NoteView : MvpView {
    val saveIntent: Observable<Note>
    val loadIntent: Observable<Int>
    val updateIntent: Observable<Note>

    fun render(state: NoteViewState)
}