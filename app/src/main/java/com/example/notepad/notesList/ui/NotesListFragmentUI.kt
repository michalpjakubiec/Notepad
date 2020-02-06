package com.example.notepad.notesList.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
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
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: LinearLayoutManager
    var isProgressVisible = false

    override fun createView(ui: AnkoContext<T>) = with(ui) {

        verticalLayout {
            lparams(matchParent, matchParent)

            verticalLayout {
                lparams(matchParent, wrapContent)
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

                progressBar = progressBar {
                    visibility = View.GONE
                    isIndeterminate = false
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    gravity = Gravity.CENTER
                }
            }


            mAdapter = NotesAdapter(context)
            layoutManager = LinearLayoutManager(context)
            mRecycler = recyclerView {
                layoutManager = layoutManager
                adapter = mAdapter
            }
        }
    }

    fun showProgress() {
        doAsync {
            uiThread {
                progressBar.visibility = View.VISIBLE
                isProgressVisible = true
            }
        }
    }

    fun hideProgress() {
        doAsync {
            uiThread {
                progressBar.visibility = View.GONE
                isProgressVisible = false
            }
        }
    }
}