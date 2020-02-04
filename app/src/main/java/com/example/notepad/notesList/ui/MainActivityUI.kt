package com.example.notepad.notesList.ui

import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivityUI<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>): View = ui.apply {
        verticalLayout {
            val name = editText()
            button("Say Hello") {
                onClick { ctx.toast("Hello, ${name.text}!") }
            }
        }
    }.view

}