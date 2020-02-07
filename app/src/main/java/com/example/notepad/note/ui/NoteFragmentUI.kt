package com.example.notepad.note.ui

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

class NoteFragmentUI<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>) = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
        }
    }
}