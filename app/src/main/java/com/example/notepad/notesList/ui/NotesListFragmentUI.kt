package com.example.notepad.notesList.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.adapter.NotesAdapter
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Observable
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import kotlin.collections.ArrayList

class NotesListFragmentUI<T>(private val listener: (Observable<Note>) -> Unit) : AnkoComponent<T> {
    lateinit var mEtSearch: TextInputEditText
    lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: NotesAdapter

    override fun createView(ui: AnkoContext<T>) = with(ui) {

        verticalLayout {
            lparams(matchParent, matchParent)

            textInputLayout {
                isErrorEnabled = true

                mEtSearch = textInputEditText {
                    textSize = 16f
                    hint = context.resources.getString(R.string.search)
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }//.lparams(matchParent, matchParent) java.lang.ClassCastException:
            }.lparams(matchParent, wrapContent)

            verticalLayout {
                lparams(matchParent, wrapContent)

                mAdapter = NotesAdapter(
                    context,
                    ArrayList(),
                    listener
                )

                mRecycler = recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter
                }
            }
        }
    }
}