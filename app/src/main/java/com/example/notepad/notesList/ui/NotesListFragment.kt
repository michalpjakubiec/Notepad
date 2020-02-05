package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.db.Repository
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
    private lateinit var ui: NotesListFragmentUI<NotesListFragment>
    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)
    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString() }

    override fun render(state: NotesListViewState) {
        if (ui.mEtSearch.isFocused && state.isSearchCompleted) {
            ui.mAdapter.notes.clear()
            ui.mAdapter.notes = state.notesList
            ui.mAdapter.notifyDataSetChanged()
        }

        if (ui.mEtSearch.isFocused && state.isSearchFailed)
            ui.mEtSearch.error = state.error

        if (ui.mEtSearch.isFocused && state.isSearchCanceled) {
            ui.mAdapter.notes.clear()
            ui.mAdapter.notes = state.notesList
            ui.mAdapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAdapter(initNotes(30))
    }

    private fun initNotes(quantity: Int): ArrayList<Note> {
        val repo = Repository<Note>(context!!)

        var notes = repo.getItemsList(Repository.allNotes)
        if (notes.isNotEmpty())
            return notes

        val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
        notes = ArrayList()
        for (i in 1..quantity) {
            notes.add(Note(UUID.randomUUID().toString(), Date(), "Title $i",
                (1..150).map { Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")))
        }

        repo.saveItemsList(Repository.allNotes, notes)
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