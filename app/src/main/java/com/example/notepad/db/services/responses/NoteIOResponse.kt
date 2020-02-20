package com.example.notepad.db.services.responses

import com.example.notepad.db.models.Note

data class NoteIOResponse(
    val error: String = "",
    val note: Note? = null
)