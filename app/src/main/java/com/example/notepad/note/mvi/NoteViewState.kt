package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateBase
import com.example.notepad.note.utils.NoteOperationResult

data class NoteViewState(
    var noteOperationResult: NoteOperationResult = NoteOperationResult.NotStarted,
    var showValidationError: Boolean = false,
    var finishActivity: Boolean = false,
    var changeFavouritesIcon: Boolean = false,
    var updateTextEdits: Boolean = false
) : ViewStateBase