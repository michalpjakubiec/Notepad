package com.example.notepad.note.utils

import com.example.notepad.db.models.Note

sealed class NoteOperationResult {
    object NotStarted : NoteOperationResult()
    object Pending : NoteOperationResult()
    data class Completed(val result: Note?) : NoteOperationResult()
    data class Failed(val error: String?) : NoteOperationResult()
}