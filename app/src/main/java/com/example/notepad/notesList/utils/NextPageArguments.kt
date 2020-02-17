package com.example.notepad.notesList.utils

data class NextPageArguments(
    var query: String,
    var skipElements: Int,
    var filters: NotesListFilterArguments
)