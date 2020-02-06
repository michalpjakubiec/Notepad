package com.example.notepad.notesList.utils

sealed class NotesListDeleteNoteResult {
    data class Completed(val id: Int) : NotesListDeleteNoteResult()
}