package com.example.notepad.components.note

import android.content.Context
import android.text.InputType
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toolbar
import com.example.notepad.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout

class NoteFragmentUI(context: Context) : LinearLayout(context) {
    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    lateinit var toolbar: Toolbar
    lateinit var saveMenuItem: MenuItem
    lateinit var favouriteMenuItem: MenuItem

    init {
        relativeLayout {
            lparams(matchParent, matchParent)

            appBarLayout {
                toolbar = toolbar {
                    menu.apply {
                        favouriteMenuItem = add(R.string.toolBarFavouriteTitle).apply {
                            icon = context.getDrawable(R.drawable.ic_favorite_border_white_24dp)
                            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                        }
                        saveMenuItem = add(R.string.toolBarSaveTitle).apply {
                            icon = context.getDrawable(R.drawable.ic_save_white_24dp)
                            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                        }
                    }
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
}