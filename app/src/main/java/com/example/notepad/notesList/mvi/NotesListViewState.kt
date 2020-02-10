package com.example.notepad.notesList.mvi

import com.example.notepad.base.ViewStateBase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult

data class NotesListViewState(
    var noteOperationResult: NoteOperationResult = NoteOperationResult.NotStarted,
    var notesListOperationResult: NotesListOperationResult = NotesListOperationResult.NotStarted,
    var showFilterBarError: Boolean = false,
    var redirectToNoteFragment: Boolean = false,
    var deleteChangedNoteFromView: Boolean = false
) : ViewStateBase