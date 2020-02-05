package com.example.notepad.notesList.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.db.NoteRepository
import com.example.notepad.db.models.Note
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
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
                val database = NoteRepository.getInstance(holder.itemView.context)
                database.update(note)

                note.isArchival
            }.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                setItemViewArchivalTheme(holder.itemView, holder.btArchive, it)
            }
        )
    }

    private fun setItemViewArchivalTheme(view: View, btn: Button, isArchival: Boolean?) {
        doAsync {
            this.uiThread {
                if (isArchival == true) {
                    btn.visibility = View.GONE
                    view.setBackgroundColor(view.context.getColor(R.color.colorArchive))
                }
            }
        }
    }

    override fun getItemCount(): Int = notes.size
}
