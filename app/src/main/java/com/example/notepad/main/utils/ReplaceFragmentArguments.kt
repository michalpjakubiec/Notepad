package com.example.notepad.main.utils

data class ReplaceFragmentArguments(
    val noteId: Int,
    val redirectToNoteFragment: Boolean,
    val redirectToNotesListFragment: Boolean
)