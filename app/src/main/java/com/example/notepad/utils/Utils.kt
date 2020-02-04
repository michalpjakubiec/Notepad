package com.example.notepad.utils

import com.example.notepad.db.models.Note

fun ArrayList<Note>?.filterByTitle(query: String): List<Note> {
    this ?: return ArrayList()
    return this.filter { x -> x.title!!.contains(query) }.toList()
}