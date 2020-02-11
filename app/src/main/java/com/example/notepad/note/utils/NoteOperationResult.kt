package com.example.notepad.note.utils

sealed class NoteOperationResult {
    object NotStarted : NoteOperationResult()
    object Pending : NoteOperationResult()
    object Completed : NoteOperationResult()
    data class Failed(val error: String) : NoteOperationResult()
}