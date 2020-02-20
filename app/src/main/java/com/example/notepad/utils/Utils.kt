package com.example.notepad.utils

import com.example.notepad.db.models.Note
import java.text.SimpleDateFormat
import java.util.*

fun String?.removeWhiteCharacters(): String {
    this ?: return ""

    return this.replace(" ", "")
}

fun Date?.toSimpleString(): String {
    this ?: return ""

    val format = SimpleDateFormat("dd.MM.yyy", Locale.getDefault())
    return format.format(this)
}

fun Note.sameContent(note: Note): Boolean {
    return this.isArchival == note.isArchival
            && this.content == note.content
            && this.title == note.title
            && this.id == note.id
}