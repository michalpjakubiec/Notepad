package com.example.notepad.components.notesList

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.notepad.R
import org.jetbrains.anko.*

class NoteViewHolderUI(context: Context) : LinearLayout(context) {

    lateinit var mTvTitle: TextView
    lateinit var mTvDate: TextView
    lateinit var mTvContent: TextView
    lateinit var mBtArchive: Button
    lateinit var mFavIcon: ImageView

    init {
        verticalLayout {
            this.orientation = VERTICAL
            lparams(matchParent, matchParent)

            relativeLayout {
                mTvTitle = textView {
                    id = View.generateViewId()
                    textSize = 18f
                }.lparams {
                    alignParentStart()
                    alignParentTop()
                }

                mFavIcon = imageView{
                    id = View.generateViewId()
                }.lparams{
                    endOf(mTvTitle)
                    alignParentTop()
                }

                mTvDate = textView {
                    id = View.generateViewId()
                    textSize = 18f
                }.lparams {
                    alignParentTop()
                    centerHorizontally()
                }

                mBtArchive = button {
                    id = View.generateViewId()
                    text = context.resources.getText(R.string.btArchive)
                }.lparams {
                    alignParentEnd()
                    alignParentTop()
                }
            }.lparams(matchParent, wrapContent)

            mTvContent = textView {
                id = View.generateViewId()
                textSize = 14f
            }.lparams(matchParent, wrapContent)
        }
    }
}