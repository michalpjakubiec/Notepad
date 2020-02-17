package com.example.notepad.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = false)
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int = -1,
    @get:JsonProperty("created")
    @set:JsonProperty("created")
    var created: Long = Date().time,
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String = "",
    @get:JsonProperty("content")
    @set:JsonProperty("content")
    var content: String = "",
    @get:JsonProperty("isArchival")
    @set:JsonProperty("isArchival")
    var isArchival: Boolean = false,
    @get:JsonProperty("isFavourite")
    @set:JsonProperty("isFavourite")
    var isFavourite: Boolean = false
)