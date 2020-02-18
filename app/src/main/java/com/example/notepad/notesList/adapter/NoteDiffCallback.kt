package com.example.notepad.notesList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.notepad.db.models.Note


class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
}