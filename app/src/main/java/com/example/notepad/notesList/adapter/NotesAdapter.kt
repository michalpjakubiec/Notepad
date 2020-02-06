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
import org.jetbrains.anko.AnkoContext


class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes: ArrayList<Note> = ArrayList()
    var pageNumber: Int = 0
    private var compositeDisposable = CompositeDisposable()

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

    fun setItems(items: List<Note>) {
        this.notes.clear()
        addItems(items)
    }

    fun addItems(items: List<Note>) {
        notes.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bindItem(note, position)

        compositeDisposable.add(Observable.create<Boolean> { emitter ->
            holder.btArchive.setOnClickListener {
                emitter.onNext((it as Button).isVisible)
            }
            emitter.setCancellable {
                holder.btArchive.setOnClickListener(null)
            }

        }.observeOn(Schedulers.io())
            .map {
                note.isArchival = true
                val database = NoteDatabase.get(holder.itemView.context).noteDao()
                database.update(note)

                note.isArchival
            }.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                holder.setItemViewArchivalTheme(holder.itemView, holder.btArchive, it)
            }
        )
    }

    override fun getItemCount(): Int = notes.size
}
