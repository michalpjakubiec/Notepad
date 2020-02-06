package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.ioThread
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
import java.util.concurrent.Executors

class NotesListFragment : MviFragment<NotesListView, NotesListPresenter>(), NotesListView {
    private lateinit var ui: NotesListFragmentUI<NotesListFragment>
    private val paginator: PublishProcessor<Int> = PublishProcessor.create()
    private val db: NoteDao by lazy { NoteDatabase.get(context!!).noteDao() }
    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString().trim() }
    override val nextPageIntent: Observable<Int>
        get() = paginator.toObservable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnScrollListener()
        ioThread {
            ui.mAdapter.notes = ArrayList(db.allNotesOrderByDateLimit(10, 0))
        }
    }

    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)

    override fun render(state: NotesListViewState) {
        if (ui.mEtSearch.isFocused && state.isSearchCompleted)
            ui.mAdapter.setItems(state.notesList)

        if (ui.mEtSearch.isFocused && state.isSearchFailed)
            ui.mEtSearch.error = state.error

        if (ui.mEtSearch.isFocused && state.isSearchCanceled) {
            state.isSearchCanceled = false
            ui.mAdapter.notes.clear()
            ui.mAdapter.pageNumber = -1
            paginator.onNext(ui.mAdapter.incrementPage())
        }

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
                val layoutManager = ui.mRecycler.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!ui.isProgressVisible && totalItemCount <= lastVisibleItem + 2) {
                    ui.showProgress()
                    paginator.onNext(ui.mAdapter.incrementPage())
                }
            }
        })
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