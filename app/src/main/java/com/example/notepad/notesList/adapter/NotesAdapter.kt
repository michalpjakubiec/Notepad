package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.db.models.Note
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext
import kotlin.collections.ArrayList

class NotesAdapter(
    private val context: Context,
    var notes: ArrayList<Note>,
    val listener: (Observable<Note>) -> Unit
    //private val listener: PublishSubject<Note>
) : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteViewHolderUI().createView(
                AnkoContext.create(
                    context,
                    parent
                )
            ), listener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindItem(notes[position], position)
        holder.btArchive.setOnClickListener { }
    }

    override fun getItemCount(): Int = notes.size

    private fun getItemPosition(item: Note): Int = notes.indexOf(item)

    fun replaceItemsAndNotifyDataSetChanged(items: ArrayList<Note>) {
        this.notes.clear()
        this.notes = items
        this.notifyDataSetChanged()
    }

    fun refreshItem(item: Note?) {
        item ?: return
        this.notifyItemChanged(getItemPosition(item))
    }
}
