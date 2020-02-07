package com.example.notepad.note.utils

sealed class NoteSaveResult {
    object Completed : NoteSaveResult()
    data class Failed(val error: String) : NoteSaveResult()
}