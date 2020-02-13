package com.example.notepad.notesList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.notepad.db.models.Note

class NoteDiffCallback(private val newNotes: List<Note>, private val oldNotes: List<Note>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotes[oldItemPosition].id == newNotes[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldNotes.size

    override fun getNewListSize(): Int = newNotes.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newNotes[newItemPosition]
        val oldItem = oldNotes[oldItemPosition]

        return newItem.content.equals(oldItem.content)
                && newItem.title.equals(oldItem.title)
                && newItem.isArchival == oldItem.isArchival
                && newItem.isFavourite == oldItem.isFavourite

    }
}