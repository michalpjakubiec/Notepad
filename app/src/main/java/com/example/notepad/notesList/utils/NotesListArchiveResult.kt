package com.example.notepad.notesList.utils

import com.example.notepad.db.models.Note


sealed class NotesListArchiveResult {
    class Pending : NotesListArchiveResult()
    data class Completed(val archivedNote: Note) : NotesListArchiveResult()
    data class Error(val error: String) : NotesListArchiveResult()
}