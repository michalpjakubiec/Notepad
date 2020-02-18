package com.example.notepad.notesList.mvi

import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListFilterArguments
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface NotesListView : MvpView {
    val searchIntent: Observable<String>
    val nextPageIntent: Observable<Pair<String, Int>>
    val deleteIntent: Observable<Note>
    val showNoteIntent: Observable<Int>
    val updateIntent: Observable<Note>
    val initialLoadIntent: Observable<Unit>
    val refreshIntent: Observable<Unit>
    val filterIntent: Observable<NotesListFilterArguments>

    fun render(state: NotesListViewState)
}