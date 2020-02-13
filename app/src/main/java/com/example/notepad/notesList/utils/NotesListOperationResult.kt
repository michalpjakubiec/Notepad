package com.example.notepad.notesList.utils

import com.example.notepad.db.models.Note

sealed class NotesListOperationResult {
    object NotStarted : NotesListOperationResult()
    object Pending : NotesListOperationResult()
    data class Completed(val result: List<Note>) : NotesListOperationResult()
    data class Failed(val error: String) : NotesListOperationResult()
}