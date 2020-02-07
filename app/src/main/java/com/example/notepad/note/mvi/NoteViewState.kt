package com.example.notepad.note.mvi

import com.example.notepad.base.ViewStateBase

data class NoteViewState(
    var error: String = ""
) : ViewStateBase