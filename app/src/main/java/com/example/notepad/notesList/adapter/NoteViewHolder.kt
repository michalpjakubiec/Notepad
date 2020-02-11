package com.example.notepad.notesList.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.db.models.Note
import com.example.notepad.utils.toSimpleString
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.image
import java.util.*

class NoteViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    val ui: NoteViewHolderUI = containerView as NoteViewHolderUI
    lateinit var note: Note

    fun bindItem(item: Note, position: Int, listener: (Note) -> Unit) {
        note = item

        ui.mTvTitle.text = item.title
        ui.mTvDate.text = Date(item.created).toSimpleString()
        ui.mTvContent.text = item.content?.take(50)

        if (item.isArchival) {
            ui.mBtArchive.visibility = View.INVISIBLE
        } else {
            ui.mBtArchive.visibility = View.VISIBLE
            ui.mBtArchive.setOnClickListener {
                note.isArchival = true
                listener(note)
            }
        }

        if (item.isFavourite)
            ui.mFavIcon.image = itemView.context.getDrawable(R.drawable.ic_favorite_white_24dp)
        else
            ui.mFavIcon.image = itemView.context.getDrawable(R.drawable.ic_favorite_border_white_24dp)

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