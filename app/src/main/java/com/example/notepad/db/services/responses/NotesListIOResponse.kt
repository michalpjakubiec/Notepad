package com.example.notepad.db.services.responses

import com.example.notepad.db.models.Note

data class NotesListIOResponse(
    val error: String = "",
    val notes: List<Note> = listOf()
)