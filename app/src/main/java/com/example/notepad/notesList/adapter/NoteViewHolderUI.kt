package com.example.notepad.notesList.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.notepad.R
import org.jetbrains.anko.*

class NoteViewHolderUI : AnkoComponent<ViewGroup> {

    companion object {
        val tvTitleId = View.generateViewId()
        val tvDateId = View.generateViewId()
        val btArchiveId = View.generateViewId()
        val tvContentId = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {

        verticalLayout {
            this.orientation = LinearLayout.VERTICAL
            lparams(matchParent, wrapContent)

            verticalLayout {
                this.orientation = LinearLayout.HORIZONTAL

                textView {
                    id = tvTitleId
                    textSize = 18f
                }.lparams {
                    gravity = Gravity.START
                }

                textView {
                    id = tvDateId
                    textSize = 18f
                }.lparams {
                    gravity = Gravity.CENTER
                    rightMargin = dip(50)
                    leftMargin = dip(50)
                }

                button {
                    id = btArchiveId
                    text = context.resources.getText(R.string.btArchive)
                }.lparams {
                    gravity = Gravity.END
                }
            }.lparams {
                this.height = wrapContent
                this.width = matchParent
                margin = dip(5)
            }

            textView {
                id = tvContentId
                textSize = 12f
            }.lparams {
                gravity = Gravity.START
                margin = dip(5)
            }
        }
    }.view
}