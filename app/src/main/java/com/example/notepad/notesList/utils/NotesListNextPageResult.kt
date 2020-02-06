package com.example.notepad.notesList.utils

import com.example.notepad.db.models.Note

sealed class NotesListNextPageResult {
    object Pending : NotesListNextPageResult()
    data class Completed(val notesList: ArrayList<Note>) : NotesListNextPageResult()
    data class Error(val error: String) : NotesListNextPageResult()
}