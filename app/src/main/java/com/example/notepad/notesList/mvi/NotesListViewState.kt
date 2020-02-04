package com.example.notepad.notesList.mvi

import com.example.notepad.base.ViewStateBase
import com.example.notepad.db.models.Note

data class NotesListViewState(
    var isSearchPending: Boolean = false,
    var isSearchCompleted: Boolean = false,
    var isSearchFailed: Boolean = false,
    var notesList: ArrayList<Note> = ArrayList(),
    var error: String = ""
) : ViewStateBase