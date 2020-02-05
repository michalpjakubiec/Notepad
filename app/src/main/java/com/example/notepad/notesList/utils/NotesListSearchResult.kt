package com.example.notepad.notesList.utils

import com.example.notepad.db.models.Note

sealed class NotesListSearchResult {
    object Pending : NotesListSearchResult()
    data class Canceled(val notesList: ArrayList<Note>) : NotesListSearchResult()
    data class Completed(val notesList: ArrayList<Note>) : NotesListSearchResult()
    data class Error(val error: String) : NotesListSearchResult()
}