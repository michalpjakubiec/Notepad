package com.example.notepad.notesList.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.notesList.utils.NotesListSearchResult

sealed class NotesListViewStateChange : ViewStateChangeBase {
    data class NotesListChanged(val searchResult: NotesListSearchResult) :
        NotesListViewStateChange()
}