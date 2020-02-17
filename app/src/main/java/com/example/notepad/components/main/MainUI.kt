package com.example.notepad.components.main

import android.content.Context
import android.widget.LinearLayout
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

class MainUI(context: Context) : LinearLayout(context) {

    init {
        linearLayout {
            lparams(matchParent, matchParent)
        }
    }
}