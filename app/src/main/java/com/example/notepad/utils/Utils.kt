package com.example.notepad.utils

import com.example.notepad.db.models.Note
import java.util.*
import kotlin.collections.ArrayList

fun ArrayList<Note>?.filterByTitle(query: String): List<Note> {
    this ?: return ArrayList()
    return this.filter { x ->
        x.title!!.toLowerCase(Locale.ROOT).removeWhiteCharacters()
            .contains(query.removeWhiteCharacters())
    }
        .toList()
}

fun String?.removeWhiteCharacters(): String {
    this ?: return ""

    return this.replace(" ", "")
}