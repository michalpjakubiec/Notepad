package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateChangeBase

sealed class NoteViewStateChange : ViewStateChangeBase {
    data class ValidationChange(val validationResult: NoteValidationResult) : NoteViewStateChange()
    data class SaveChange(val saveResult: NoteSaveResult) : NoteViewStateChange()
}