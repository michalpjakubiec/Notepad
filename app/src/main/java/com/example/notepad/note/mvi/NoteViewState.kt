package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateBase
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult

data class NoteViewState(
    var noteOperationResult: NoteOperationResult = NoteOperationResult.NotStarted,
    var showValidationError: Boolean = false,
    var updateNote: Boolean = false,
    var noteLoadSaveResult: NoteLoadSaveResult = NoteLoadSaveResult.NotStarted,
    var finishActivity: Boolean = false
) : ViewStateBase