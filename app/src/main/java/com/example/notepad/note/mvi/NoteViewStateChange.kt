package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult

sealed class NoteViewStateChange : ViewStateChangeBase {
    data class SaveChange(val saveResult: NoteLoadSaveResult) : NoteViewStateChange()
    data class NoteChange(val loadResult: NoteLoadSaveResult) : NoteViewStateChange()
    data class NoteDetailsChanged(val updateResult: NoteOperationResult) : NoteViewStateChange()
}