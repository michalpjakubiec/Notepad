package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.note.utils.NoteSaveResult
import com.example.notepad.note.utils.NoteValidationResult

sealed class NoteViewStateChange : ViewStateChangeBase {
    data class ValidationChange(val validationResult: NoteValidationResult) : NoteViewStateChange()
    data class SaveChange(val saveResult: NoteSaveResult) : NoteViewStateChange()
}