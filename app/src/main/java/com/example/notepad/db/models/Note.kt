package com.example.notepad.db.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey
    @NonNull
    val id: String,
    val created: Long,
    var title: String?,
    var content: String?,
    var isArchival: Boolean = false,
    var isFavourite: Boolean = false
)