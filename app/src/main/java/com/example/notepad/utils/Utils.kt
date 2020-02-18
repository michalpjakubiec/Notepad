package com.example.notepad.utils

import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
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

fun Note.sameAs(note: Note): Boolean {
    return this.isFavourite == note.isFavourite
            && this.isArchival == note.isArchival
            && this.content == note.content
            && this.title == note.title
            && this.id == note.id
}

fun Note.sameContent(note: Note): Boolean {
    return this.isArchival == note.isArchival
            && this.content == note.content
            && this.title == note.title
            && this.id == note.id
}

fun MenuItem.tripleStageOnClick(icons: List<Drawable>, maxSize: Int) {
    var index = (this as View).tag as Int
    index++
    if (index < maxSize - 1)
        index = 0

    this.icon = icons[index]
}