package com.example.notepad.note.ui

import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toolbar
import androidx.core.view.marginTop
import com.example.notepad.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout

class NoteFragmentUI<T> : AnkoComponent<T> {
    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            appBarLayout {
                toolbar = toolbar {
                    inflateMenu(R.menu.toolbar)
                }.lparams(matchParent, wrapContent)

            }.lparams(matchParent, wrapContent)

            verticalLayout {
                etTitle = editText {
                    textSize = 18f
                    hint = context.getString(R.string.titleHint)
                    inputType = InputType.TYPE_CLASS_TEXT
                    maxLines = 1
                }.lparams(matchParent, wrapContent)

                etContent = editText {
                    textSize = 18f
                    inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    hint = context.getString(R.string.contentHint)
                    imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                }.lparams(matchParent, wrapContent)
            }.lparams {
                alignParentTop()
                topMargin = dip(75)
                height = wrapContent
                width = matchParent
            }
        }
    }

    companion object {
        val toolbarId = View.generateViewId()
    }
}