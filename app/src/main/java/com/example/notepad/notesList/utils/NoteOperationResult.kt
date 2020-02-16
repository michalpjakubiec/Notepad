package com.example.notepad.notesList.utils

import com.example.notepad.db.models.Note

sealed class NoteOperationResult {
    object NotStarted : NoteOperationResult()
    object Pending : NoteOperationResult()
    data class Completed(val id: Int, val note: Note? = null) : NoteOperationResult()
    data class Failed(val error: String) : NoteOperationResult()
}