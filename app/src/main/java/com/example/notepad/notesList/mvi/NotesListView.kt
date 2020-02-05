package com.example.notepad.notesList.mvi

import com.example.notepad.db.models.Note
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface NotesListView : MvpView {
    val searchIntent: Observable<String>
    val archiveIntent: PublishSubject<Note>

    fun render(state: NotesListViewState)
}