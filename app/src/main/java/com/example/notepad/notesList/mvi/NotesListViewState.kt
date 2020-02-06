package com.example.notepad.notesList.mvi

import com.example.notepad.base.ViewStateBase
import com.example.notepad.db.models.Note

data class NotesListViewState(
    var isSearchCanceled: Boolean = false,
    var isSearchCompleted: Boolean = false,
    var isSearchFailed: Boolean = false,
    var isSearchPending: Boolean = false,
    var isNextPagePending: Boolean = false,
    var isNextPageCompleted: Boolean = false,
    var isNextPageFailed: Boolean = false,
    var notesList: ArrayList<Note> = ArrayList(),
    var isDeleteCompleted: Boolean = false,
    var deletedNoteId: Int = -1,
    var error: String = ""
) : ViewStateBase