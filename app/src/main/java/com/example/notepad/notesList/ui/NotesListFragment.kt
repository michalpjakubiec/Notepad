package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.example.notepad.notesList.mvi.NotesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import org.jetbrains.anko.AnkoContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class NotesListFragment : MviFragment<NotesListView, NotesListPresenter>(), NotesListView {

    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)
    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString() }
    private lateinit var ui: NotesListFragmentUI<NotesListFragment>


    override fun render(state: NotesListViewState) {
        if (ui.mEtSearch.isFocused && state.isSearchCompleted) {
            ui.mAdapter.notes.clear()
            ui.mAdapter.notes = state.notesList
            ui.mAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAdapter(initNotes(30))
    }

    private fun initNotes(quantity: Int): ArrayList<Note> {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val notes: ArrayList<Note> = ArrayList()
        for (i in 1..quantity) {
            notes.add(Note(UUID.randomUUID().toString(), Date(), "Title $i",
                (1..150).map { Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")))
        }

        return notes
    }

    private fun loadAdapter(notes: ArrayList<Note>) {
        ui.mAdapter.notes = notes
        ui.mAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = NotesListFragmentUI()
        return ui.createView(AnkoContext.create(requireContext(), this))
    }


}