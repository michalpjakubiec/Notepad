package com.example.notepad.db.response

import com.example.notepad.db.models.Note

data class NoteDbResponse(
    val error: String,
    val note: Note?
)