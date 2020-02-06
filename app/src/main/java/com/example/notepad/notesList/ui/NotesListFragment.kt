package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.*
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.example.notepad.notesList.mvi.NotesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.processors.PublishProcessor
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.toast

class NotesListFragment : MviFragment<NotesListView, NotesListPresenter>(), NotesListView {
    private lateinit var ui: NotesListFragmentUI<NotesListFragment>
    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString() }
    override lateinit var nextPageIntent: Observable<Int>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnScrollListener()
        nextPageIntent = Observable.just(incrementPage())
    }

    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)

    override fun render(state: NotesListViewState) {
        if (ui.mEtSearch.isFocused && (state.isSearchCompleted || state.isSearchCanceled))
            ui.mAdapter.setItems(state.notesList)

        if (ui.mEtSearch.isFocused && state.isSearchFailed)
            ui.mEtSearch.error = state.error

        if (state.isSearchPending || state.isNextPagePending)
            ui.showProgress()
        else
            ui.hideProgress()

        if (state.isNextPageCompleted)
            ui.mAdapter.addItems(state.notesList)

        if (state.isNextPageFailed)
            toast(state.error)
    }

    private fun setupOnScrollListener() {
        ui.mRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = ui.layoutManager.itemCount
                val lastVisibleItem = ui.layoutManager.findLastVisibleItemPosition()
                if (!ui.isProgressVisible && totalItemCount <= lastVisibleItem + 1) {
                    incrementPage()
                }
            }
        })
    }

    private var page = 0

    private fun incrementPage(): Int {
        page++
        return page
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