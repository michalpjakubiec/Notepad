package com.example.notepad.components.notesList

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notepad.R
import com.example.notepad.notesList.adapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class NotesListFragmentUI(context: Context) : LinearLayout(context) {
    lateinit var mEtSearch: TextInputEditText
    lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: NotesAdapter
    lateinit var fabAdd: FloatingActionButton
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var mainLayout: LinearLayout
    var isNextPageLoading = false

    init {
        mainLayout = verticalLayout {
            lparams(matchParent, matchParent)

            textInputLayout {
                isErrorEnabled = true

                mEtSearch = textInputEditText {
                    textSize = 16f
                    hint = context.resources.getString(R.string.search)
                    layoutParams =
                        LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                }//.lparams(matchParent, matchParent) java.lang.ClassCastException:
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            relativeLayout {
                lparams(matchParent, wrapContent)

                fabAdd = floatingActionButton {
                    imageResource = R.drawable.ic_add_white_24dp
                }.lparams {
                    height = wrapContent
                    width = wrapContent
                    margin = dip(10)
                    alignParentBottom()
                    alignParentEnd()
                }

                swipeRefreshLayout = swipeRefreshLayout {
                    mAdapter = NotesAdapter(context)
                    mRecycler = recyclerView {
                        layoutManager = LinearLayoutManager(context)
                        addItemDecoration(NoteItemDecoration(context))
                        adapter = mAdapter
                    }

                }.lparams(matchParent, matchParent)
            }
        }
    }
}