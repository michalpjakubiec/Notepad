package com.example.notepad.notesList.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult

sealed class NotesListViewStateChange : ViewStateChangeBase {
    data class DataSetChanged(val filterResult: NotesListOperationResult) :
        NotesListViewStateChange()

    data class PageChanged(val nextPageResult: NotesListOperationResult) :
        NotesListViewStateChange()

    data class NoteDeleted(val deleteNoteResult: NoteOperationResult) :
        NotesListViewStateChange()

    data class noteShowed(val showNoteResult: NoteOperationResult) :
        NotesListViewStateChange()

    data class NoteUpdated(val updateNoteResult: NoteOperationResult) :
        NotesListViewStateChange()
}