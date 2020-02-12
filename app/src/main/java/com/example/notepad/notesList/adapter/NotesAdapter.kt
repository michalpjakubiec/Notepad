package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.db.models.Note
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext


class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes: ArrayList<Note> = ArrayList()
    var pageNumber: Int = 0
    val updateItemSubject: PublishSubject<Note> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteViewHolderUI(context))
    }

    fun deletedItem(id: String) {
        val item = notes.firstOrNull { it.id == id }
        item ?: return
        this.notifyItemRemoved(notes.indexOf(item))
    }

    fun setItems(items: List<Note>) {
        val diff = NoteDiffCallback(items, notes)
        val result = DiffUtil.calculateDiff(diff)

        this.notes.clear()
        this.notes.addAll(items)
        this.pageNumber = 1

        result.dispatchUpdatesTo(this)
    }

    fun addItems(items: List<Note>) {
        val oldList = ArrayList(notes)
        notes.addAll(items)

        val diff = NoteDiffCallback(notes, oldList)
        val result = DiffUtil.calculateDiff(diff)

        pageNumber++
        result.dispatchUpdatesTo(this)
    }


    private fun noteUpdated(item: Note) {
        updateItemSubject.onNext(item)
    }

    fun updateItem(id: String) {
        val item = notes.firstOrNull { it.id == id }
        item ?: return
        this.notifyItemChanged(notes.indexOf(item))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bindItem(note, position, this::noteUpdated)
    }

    override fun getItemCount(): Int = notes.size
}
