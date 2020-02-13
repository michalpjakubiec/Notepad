package com.example.notepad.utils

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