package com.example.notepad.db.response

import com.example.notepad.db.models.Note

data class NotesListDbResponse(
    val error: String,
    val notes: List<Note>
)