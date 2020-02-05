package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.db.models.Note
import org.jetbrains.anko.AnkoContext

class NotesAdapter(
    private val context: Context,
    var notes: ArrayList<Note>,
    private val listener: (Note) -> Unit
) : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteViewHolderUI().createView(
                AnkoContext.create(
                    context,
                    parent
                )
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindItem(notes[position], position, listener)
    }

    override fun getItemCount(): Int = notes.size

    fun replaceItemsAndNotifyDataSetChanged(items: ArrayList<Note>) {
        this.notes.clear()
        this.notes = items
        this.notifyDataSetChanged()
    }
}