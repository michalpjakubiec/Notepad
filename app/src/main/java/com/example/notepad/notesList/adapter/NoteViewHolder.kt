package com.example.notepad.notesList.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.db.models.Note
import com.example.notepad.utils.toSimpleString
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class NoteViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    private val tvTitle: TextView = itemView.findViewById(NoteViewHolderUI.tvTitleId)
    private val tvDate: TextView = itemView.findViewById(NoteViewHolderUI.tvDateId)
    private val tvShortContent: TextView = itemView.findViewById(NoteViewHolderUI.tvContentId)
    private val btArchive: Button = itemView.findViewById(NoteViewHolderUI.btArchiveId)
    lateinit var note: Note

    fun bindItem(item: Note, position: Int, listener: (Note) -> Unit) {
        note = item

        tvTitle.text = item.title
        tvDate.text = Date(item.created).toSimpleString()
        tvShortContent.text = item.content?.take(50)

        if (item.isArchival) {
            btArchive.visibility = View.GONE
        } else {
            btArchive.visibility = View.VISIBLE
            btArchive.setOnClickListener {
                note.isArchival = true
                listener(note)
            }
        }

        itemView.setBackgroundColor(
            itemView.context.resources.getColor(getBackgroundColor(position, item.isArchival))
        )
    }

    private fun getBackgroundColor(position: Int, isArchival: Boolean?): Int {
        if (isArchival == true)
            return R.color.colorArchive

        return getPositionColor(position)
    }

    private fun getPositionColor(position: Int): Int {
        return when (position % 2) {
            0 -> R.color.colorEven
            else -> R.color.colorUnEven
        }
    }
}