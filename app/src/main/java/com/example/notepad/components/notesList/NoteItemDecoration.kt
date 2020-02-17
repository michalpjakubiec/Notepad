package com.example.notepad.components.notesList

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R

class NoteItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    private val divider = context.getDrawable(R.drawable.note_recycler_view_divider)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        for (i in childCount downTo 0 step 1) {
            val child = parent.getChildAt(i)

            child ?: continue

            val params =
                child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}