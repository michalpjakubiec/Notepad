package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.note.utils.NoteOperationResult

sealed class NoteViewStateChange : ViewStateChangeBase {
    data class ValidationChange(val validationResult: NoteOperationResult) : NoteViewStateChange()
    data class SaveChange(val saveResult: NoteOperationResult) : NoteViewStateChange()
    data class FavouriteChange(val favouriteResult: NoteOperationResult) : NoteViewStateChange()
}