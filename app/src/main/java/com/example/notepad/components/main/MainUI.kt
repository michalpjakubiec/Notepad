package com.example.notepad.components.main

import android.content.Context
import android.widget.LinearLayout
import com.example.notepad.utils.MAIN_ACTIVITY_CONTAINER_ID
import org.jetbrains.anko.matchParent

class MainUI(context: Context) : LinearLayout(context) {

    init {
        layoutParams = LayoutParams(matchParent, matchParent)
        id = MAIN_ACTIVITY_CONTAINER_ID
    }
}