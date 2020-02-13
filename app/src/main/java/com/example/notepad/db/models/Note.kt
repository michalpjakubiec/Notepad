package com.example.notepad.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

@Entity
data class Note(
    @PrimaryKey(autoGenerate = false)
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int,
    @get:JsonProperty("created")
    @set:JsonProperty("created")
    var created: Long,
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String?,
    @get:JsonProperty("content")
    @set:JsonProperty("content")
    var content: String?,
    @get:JsonProperty("isArchival")
    @set:JsonProperty("isArchival")
    var isArchival: Boolean,
    @get:JsonProperty("isFavourite")
    @set:JsonProperty("isFavourite")
    var isFavourite: Boolean
)