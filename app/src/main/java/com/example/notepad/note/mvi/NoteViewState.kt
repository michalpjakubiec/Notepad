package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateBase

data class NoteViewState(
    var isValidationCompleted: Boolean = false,
    var isValidationFailed: Boolean = false,
    var isValidationCanceled: Boolean = false,
    var isSavingCompleted: Boolean = false,
    var isSavingFailed: Boolean = false,
    var error: String = ""
) : ViewStateBase