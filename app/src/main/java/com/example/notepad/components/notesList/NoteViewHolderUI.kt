package com.example.notepad.components.notesList

import android.content.Context
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.notepad.R
import org.jetbrains.anko.*

class NoteViewHolderUI(context: Context) : LinearLayout(context) {

    lateinit var mTvTitle: TextView
    lateinit var mTvDate: TextView
    lateinit var mTvContent: TextView
    lateinit var mBtArchive: Button

    init {
        verticalLayout {
            this.orientation = VERTICAL
            lparams(matchParent, matchParent)

            relativeLayout {
                mTvTitle = textView {
                    textSize = 18f
                }.lparams {
                    alignParentStart()
                    alignParentTop()
                }

                mTvDate = textView {
                    textSize = 18f
                }.lparams {
                    alignParentTop()
                    centerHorizontally()
                }

                mBtArchive = button {
                    text = context.resources.getText(R.string.btArchive)
                }.lparams {
                    alignParentEnd()
                    alignParentTop()
                }
            }.lparams (matchParent, wrapContent)

            mTvContent = textView {
                textSize = 14f
            }.lparams (matchParent, wrapContent)
        }
    }
}