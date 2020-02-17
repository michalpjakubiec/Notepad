package com.example.notepad.components.notesList

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*

class NoteViewHolderUI(context: Context) : LinearLayout(context) {

    lateinit var mTvTitle: TextView
    lateinit var mTvDate: TextView
    lateinit var mTvContent: TextView
    lateinit var mBtArchive: ImageButton
    lateinit var mIbFav: ImageButton

    init {
        verticalLayout {
            this.orientation = VERTICAL
            lparams(matchParent, matchParent)

            verticalLayout {
                relativeLayout {
                    mTvTitle = textView {
                        id = View.generateViewId()
                        textSize = 18f
                    }.lparams {
                        alignParentStart()
                        alignParentTop()
                    }

                    mIbFav = imageButton {
                        id = View.generateViewId()
                        backgroundColor = Color.TRANSPARENT
                    }.lparams {
                        alignParentTop()
                        marginStart = dip(70)
                    }

                    mTvDate = textView {
                        id = View.generateViewId()
                        textSize = 18f
                    }.lparams {
                        alignParentTop()
                        centerHorizontally()
                    }

                    mBtArchive = imageButton {
                        id = View.generateViewId()
                        backgroundColor = Color.TRANSPARENT
                    }.lparams {
                        alignParentEnd()
                        alignParentTop()
                    }
                }.lparams(matchParent, wrapContent)

                mTvContent = textView {
                    id = View.generateViewId()
                    textSize = 14f
                }.lparams(matchParent, wrapContent)

            }.lparams {
                width = matchParent
                height = matchParent
                margin = dip(15)
            }
        }
    }
}