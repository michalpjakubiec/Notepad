package com.example.notepad.notesList.utils

import com.example.notepad.db.models.Note

sealed class NotesListSearchResult {
    object Pending : NotesListSearchResult()
    object Canceled : NotesListSearchResult()
    data class Completed(val notesList: ArrayList<Note>) : NotesListSearchResult()
    data class Error(val error: String) : NotesListSearchResult()
}