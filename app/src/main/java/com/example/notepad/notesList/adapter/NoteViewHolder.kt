package com.example.notepad.notesList.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.db.models.Note
import kotlinx.android.extensions.LayoutContainer

class NoteViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    private val tvTitle: TextView = itemView.findViewById(NoteViewHolderUI.tvTitleId)
    private val tvShortContent: TextView = itemView.findViewById(NoteViewHolderUI.tvContentId)

    fun bindItem(item: Note, position: Int, listener: (Note) -> Unit) {
        tvTitle.text = item.title
        tvShortContent.text = item.content?.take(50)

        containerView.setBackgroundColor(
            itemView.context.resources.getColor(getPositionColor(position))
        )

        containerView.setOnClickListener { listener(item) }
    }

    private fun getPositionColor(position: Int): Int {
        return when (position % 2) {
            0 -> R.color.colorPrimary
            else -> R.color.colorAccent
        }
    }
}