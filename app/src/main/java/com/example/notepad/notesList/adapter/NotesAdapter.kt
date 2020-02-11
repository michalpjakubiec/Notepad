package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes: ArrayList<Note> = ArrayList()
    var pageNumber: Int = 0
    val updateItemSubject: PublishSubject<Note> = PublishSubject.create()

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

    fun deletedItem(id: Int) {
        val item = notes.firstOrNull { it.id == id }
        item ?: return
        this.notifyItemRemoved(notes.indexOf(item))
    }

    fun incrementPage(): Int {
        pageNumber++
        return pageNumber
    }

    fun setItems(items: List<Note>) {
        this.notes.clear()
        this.pageNumber = 0
        addItems(items)
    }

    fun addItems(items: List<Note>) {
        notes.addAll(items)
        notifyDataSetChanged()
    }


    //DiffUtil && AsyncDiffUtil
    private fun setItemArchive(item: Note) {
        updateItemSubject.onNext(item)
    }

    fun updateItem(id: Int) {
        val item = notes.firstOrNull { it.id == id }
        item ?: return
        this.notifyItemChanged(notes.indexOf(item))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bindItem(note, position, this::setItemArchive)
    }

    override fun getItemCount(): Int = notes.size
}
