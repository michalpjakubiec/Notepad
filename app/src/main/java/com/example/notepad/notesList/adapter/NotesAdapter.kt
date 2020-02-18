package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.db.models.Note
import io.reactivex.subjects.PublishSubject


class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteViewHolder>() {

    var pageNumber: Int = 0
    private val differ = AsyncListDiffer(this, NoteDiffCallback())
    val updateItemSubject: PublishSubject<Note> = PublishSubject.create()
    val longClickSubject: PublishSubject<Int> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteViewHolderUI(context))
    }

    fun deletedItem(id: Int) {
        val newList = ArrayList(differ.currentList)
        newList.removeIf { it.id == id }

        differ.submitList(newList)
    }

    fun setItems(items: List<Note>) {
        differ.submitList(items)
        this.pageNumber = 1
    }

    fun addItems(items: List<Note>) {
        val currentList = ArrayList(differ.currentList)
        currentList.addAll(items)

        pageNumber++
        differ.submitList(currentList)
    }


    private fun noteUpdated(item: Note) {
        updateItemSubject.onNext(item)
    }

    private fun longClicked(item: Note) {
        longClickSubject.onNext(item.id)
    }

    fun updateItem(itemId: Int, note: Note) {
        val newList = ArrayList(differ.currentList)
        val index = newList.indexOfFirst { it.id == itemId }
        newList[index] = note

        differ.submitList(newList)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindItem(
            differ.currentList[position],
            position,
            this::noteUpdated,
            this::longClicked
        )
    }

    override fun getItemCount(): Int = differ.currentList.size
}
