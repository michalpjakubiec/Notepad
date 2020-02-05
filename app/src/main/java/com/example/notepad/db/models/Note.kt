package com.example.notepad.db.models

import java.util.*

data class Note(
    val id: String?,
    val created: Date?,
    var title: String?,
    var content: String?,
    var isArchival: Boolean?
)