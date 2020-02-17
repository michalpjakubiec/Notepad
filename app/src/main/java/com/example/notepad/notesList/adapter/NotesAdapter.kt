package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.db.models.Note
import io.reactivex.subjects.PublishSubject


class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes: ArrayList<Note> = ArrayList()
    var pageNumber: Int = 0
    val updateItemSubject: PublishSubject<Note> = PublishSubject.create()
    val longClickSubject: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteViewHolderUI(context))
    }

    fun deletedItem(id: Int) {
        val newList = ArrayList(notes)
        newList.removeIf { it.id == id }

        val diff = NoteDiffCallback(newList, notes)
        val result = DiffUtil.calculateDiff(diff)

        this.notes.clear()
        this.notes.addAll(newList)

        result.dispatchUpdatesTo(this)
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
        if (items.isEmpty()) return

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

    private fun longClicked(item: Note) {
        longClickSubject.onNext(item.id)
    }

    fun updateItem(itemId: Int, note: Note) {
        val index = this.notes.indexOfFirst { it.id == itemId }
        notes[index] = note
        this.notifyItemChanged(index)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]


        holder.bindItem(note, position, this::noteUpdated, this::longClicked)
    }

    override fun getItemCount(): Int = notes.size
}
