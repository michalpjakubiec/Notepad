package com.example.notepad.notesList.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.db.models.Note
import com.example.notepad.utils.toSimpleString
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import java.util.*

class NoteViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    private val ui: NoteViewHolderUI = containerView as NoteViewHolderUI
    lateinit var note: Note

    fun bindItem(
        item: Note,
        position: Int,
        updateListener: (Note) -> Unit,
        longClickListener: (Note) -> Unit
    ) {
        note = item

        ui.mTvTitle.text = item.title
        ui.mTvDate.text = Date(item.created).toSimpleString()
        val shortContent =
            if (item.content?.length != null && item.content!!.length > 15) item.content?.take(15) + "..." else item.content
        ui.mTvContent.text = shortContent

        if (item.isArchival) {
            ui.mBtArchive.visibility = View.INVISIBLE
        } else {
            ui.mBtArchive.visibility = View.VISIBLE
            ui.mBtArchive.setOnClickListener {
                note.isArchival = true
                updateListener(note)
            }

            itemView.onLongClick {
                longClickListener(note)
            }
        }

        ui.mIbFav.setOnClickListener {
            note.isFavourite = !note.isFavourite
            updateListener(note)
        }

        if (item.isFavourite)
            ui.mIbFav.image = itemView.context.getDrawable(R.drawable.ic_favorite_white_24dp)
        else
            ui.mIbFav.image = itemView.context.getDrawable(R.drawable.ic_favorite_border_white_24dp)

        itemView.background =
            itemView.context.getDrawable(getBackgroundColor(position, item.isArchival))
    }

    private fun getBackgroundColor(position: Int, isArchival: Boolean?): Int {
        if (isArchival == true)
            return R.drawable.note_view_holder_background_archival

        return getPositionColor(position)
    }

    private fun getPositionColor(position: Int): Int {
        return when (position % 2) {
            0 -> R.drawable.note_view_holder_background_even
            else -> R.drawable.note_view_holder_background_uneven
        }
    }
}