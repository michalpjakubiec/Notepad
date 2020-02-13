package com.example.notepad.note.mvi

import com.example.notepad.db.models.Note
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface NoteView : MvpView {
    val saveIntent: Observable<Note>
    val validationIntent: Observable<String>
    val favouriteIntent: Observable<Note>
    val loadingIntent: Observable<Int>

    fun render(state: NoteViewState)
}