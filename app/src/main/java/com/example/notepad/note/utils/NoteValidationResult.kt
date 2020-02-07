package com.example.notepad.note.utils

sealed class NoteValidationResult {
    object Completed : NoteValidationResult()
    data class Failed(val error: String) : NoteValidationResult()
}