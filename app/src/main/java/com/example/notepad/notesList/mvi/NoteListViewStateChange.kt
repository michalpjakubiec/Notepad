package com.example.notepad.notesList.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.notesList.utils.NotesListDeleteNoteResult
import com.example.notepad.notesList.utils.NotesListNextPageResult
import com.example.notepad.notesList.utils.NotesListSearchResult

sealed class NotesListViewStateChange : ViewStateChangeBase {
    data class NotesListChanged(val searchResult: NotesListSearchResult) :
        NotesListViewStateChange()

    data class NotesPageChanged(val nextPageResult: NotesListNextPageResult) :
        NotesListViewStateChange()

    data class NoteDeleted(val deleteNoteResult: NotesListDeleteNoteResult) :
        NotesListViewStateChange()
}