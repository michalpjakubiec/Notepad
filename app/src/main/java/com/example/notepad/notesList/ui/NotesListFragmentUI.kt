package com.example.notepad.notesList.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.notesList.adapter.NotesAdapter
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

class NotesListFragmentUI<T> : AnkoComponent<T> {
    lateinit var mEtSearch: TextInputEditText
    lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: NotesAdapter

    override fun createView(ui: AnkoContext<T>) = with(ui) {

        textInputLayout {
            lparams(matchParent, wrapContent)

            mEtSearch = textInputEditText {
                textSize = 16f
                hint = context.resources.getString(R.string.search)
            }.lparams(matchParent, matchParent)

            verticalLayout {
                lparams(matchParent, matchParent)

                mAdapter = NotesAdapter(
                    context,
                    ArrayList()
                ) {
                    //startActivity<NoteActivity>("noteBundle" to it)
                }

                mRecycler = recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter
                }
            }
        }
    }
}