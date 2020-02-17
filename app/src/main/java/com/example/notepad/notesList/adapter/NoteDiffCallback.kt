package com.example.notepad.notesList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.notepad.db.models.Note
import com.example.notepad.utils.sameAs


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

        return newItem.sameAs(oldItem)
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val newItem = newNotes[newItemPosition]
//        val oldItem = oldNotes[oldItemPosition]
//
//        val bundle = Bundle()
//        if (oldItem.title != newItem.title)
//            bundle.putString("title", newItem.title)
//
//        if (oldItem.content != newItem.content)
//            bundle.putString("content", newItem.content)
//
//        if (oldItem.isArchival != newItem.isArchival)
//            bundle.putBoolean("archival", newItem.isArchival)
//
//        if (oldItem.isFavourite != newItem.isFavourite)
//            bundle.putBoolean("archival", newItem.isFavourite)
//
//        return bundle
//    }
}