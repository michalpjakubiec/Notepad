package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.db.NoteRepository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.example.notepad.notesList.mvi.NotesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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
        if (ui.mEtSearch.isFocused && (state.isSearchCompleted || state.isSearchCanceled))
            ui.mAdapter.notes = state.notesList

        if (ui.mEtSearch.isFocused && state.isSearchFailed)
            ui.mEtSearch.error = state.error
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.mAdapter.notes = initNotes(30)
    }

    private fun initNotes(quantity: Int): List<Note> {
        val repo = NoteRepository.getInstance(context!!)

        var notes = repo.getAll(NoteRepository.notesTableKey)
        if (notes.isNotEmpty())
            return notes

        val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
        notes = ArrayList()
        for (i in 1..quantity) {
            notes.add(Note(UUID.randomUUID().toString(), Date(), "Title $i",
                (1..150).map { Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString(""), false))
        }

        repo.save(NoteRepository.notesTableKey, notes)
        return notes
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