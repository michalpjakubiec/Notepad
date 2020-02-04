package com.example.notepad.notesList.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

class NoteViewHolderUI : AnkoComponent<ViewGroup> {

    companion object {
        val tvTitleId = View.generateViewId()
        val tvContentId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {
        verticalLayout {
            this.orientation = LinearLayout.VERTICAL
            lparams(matchParent, wrapContent)
            padding = dip(16)

            textView {
                id = tvTitleId
                textSize = 18f
            }.lparams {
                gravity = Gravity.CENTER
                margin = dip(10)
            }

            textView {
                id = tvContentId
                textSize = 12f
            }.lparams {
                gravity = Gravity.START
                margin = dip(10)
            }
        }
    }.view
}