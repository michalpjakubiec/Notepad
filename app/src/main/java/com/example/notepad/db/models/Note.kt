package com.example.notepad.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val created: Long,
    var title: String?,
    var content: String?,
    var isArchival: Boolean
)