package com.example.notepad.utils

import android.view.View
import com.example.notepad.db.models.Note
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun List<Note>?.filterByTitle(query: String): List<Note> {
    this ?: return listOf()
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

fun Date?.toSimpleString(): String {
    this ?: return ""

    val format = SimpleDateFormat("dd.MM.yyy", Locale.getDefault())
    return format.format(this)
}