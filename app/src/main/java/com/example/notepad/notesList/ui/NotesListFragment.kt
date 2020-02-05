package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notepad.R
import com.example.notepad.db.Repository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.example.notepad.notesList.mvi.NotesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class NotesListFragment : MviFragment<NotesListView, NotesListPresenter>(), NotesListView {
    private lateinit var ui: NotesListFragmentUI<NotesListFragment>
    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)
    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString() }
    override val archiveIntent: Observable<Note>

    override fun render(state: NotesListViewState) {
        if (ui.mEtSearch.isFocused && (state.isSearchCompleted || state.isSearchCanceled))
            ui.mAdapter.replaceItemsAndNotifyDataSetChanged(state.notesList)

        if (ui.mEtSearch.isFocused && state.isSearchFailed)
            ui.mEtSearch.error = state.error

        if (state.isArchiveCompleted)
            ui.mAdapter.refreshItem(state.archivedNote)

        if (state.isArchiveFailed)
            toast(state.error)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.mAdapter.replaceItemsAndNotifyDataSetChanged(initNotes(30))
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
                    .joinToString(""), false))
        }

        repo.saveItemsList(Repository.allNotes, notes)
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