package com.example.notepad.notesList.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.notesList.adapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.textInputEditText
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

class NotesListFragmentUI<T> : AnkoComponent<T> {
    lateinit var mEtSearch: TextInputEditText
    lateinit var mRecycler: RecyclerView
    lateinit var mAdapter: NotesAdapter
    lateinit var progressDialog: Dialog
    lateinit var fabAdd: FloatingActionButton
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
                        layoutParams =
                            LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                    }//.lparams(matchParent, matchParent) java.lang.ClassCastException:
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                relativeLayout {
                    lparams(wrapContent, matchParent)

                    fabAdd = floatingActionButton {
                        imageResource = R.drawable.ic_add_white_24dp
                    }.lparams {
                        height = wrapContent
                        width = wrapContent
                        margin = dip(10)
                        alignParentBottom()
                        alignParentEnd()
                    }

                    mAdapter = NotesAdapter(context)
                    mRecycler = recyclerView {
                        layoutManager = LinearLayoutManager(context)
                        adapter = mAdapter
                    }.lparams(matchParent, matchParent)
                }
            }

            progressDialog = alert {
                customView {
                    progressBar {
                        isIndeterminate = false
                    }.lparams(matchParent, matchParent)
                }
            }.build() as Dialog
        }
    }

    fun showProgress() {
        isProgressVisible = true
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.show()
    }

    fun hideProgress() {
        isProgressVisible = false
        progressDialog.dismiss()
    }

//    fun showProgress() {
//        doAsync {
//            uiThread {
//                isProgressVisible = true
//                progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                progressDialog.show()
//            }
//        }
//    }
//
//    fun hideProgress() {
//        doAsync {
//            uiThread {
//                isProgressVisible = false
//                progressDialog.dismiss()
//            }
//        }
//    }
}