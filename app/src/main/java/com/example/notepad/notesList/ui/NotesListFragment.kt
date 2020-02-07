package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.ioThread
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.adapter.NoteViewHolder
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.example.notepad.notesList.mvi.NotesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.processors.PublishProcessor
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.toast


class NotesListFragment : MviFragment<NotesListView, NotesListPresenter>(), NotesListView {
    private lateinit var ui: NotesListFragmentUI<NotesListFragment>
    private val db: NoteDao by lazy { NoteDatabase.get(context!!).noteDao() }
    private val nextPagePublisher: PublishProcessor<Pair<Int, String>> = PublishProcessor.create()
    private val deletePublisher: PublishProcessor<Note> = PublishProcessor.create()

    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString().trim() }
    override val nextPageIntent: Observable<Pair<Int, String>>
        get() = nextPagePublisher.toObservable()
    override val deleteIntent: Observable<Note>
        get() = deletePublisher.toObservable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnScrollListener()
        initSwipeToDelete()
        reloadData()
    }

    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)

    override fun render(state: NotesListViewState) {
        if (ui.mEtSearch.isFocused && state.isSearchCompleted)
            ui.mAdapter.setItems(state.notesList)

        if (ui.mEtSearch.isFocused && state.isSearchFailed)
            ui.mEtSearch.error = state.error

        if (ui.mEtSearch.isFocused && state.isSearchCanceled) {
            state.isSearchCanceled = false
            reloadData()
        }

        if (state.isSearchPending || state.isNextPagePending)
            ui.showProgress()
        else
            ui.hideProgress()

        if (state.isNextPageCompleted)
            ui.mAdapter.addItems(state.notesList)

        if (state.isNextPageFailed)
            toast(state.error)

        if (state.isDeleteCompleted && state.deletedNoteId != -1)
            ui.mAdapter.deletedItem(state.deletedNoteId)
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
                    nextPagePublisher.onNext(
                        Pair(
                            ui.mAdapter.incrementPage(),
                            ui.mEtSearch.text.toString()
                        )
                    )
                    return
                }
            }
        })
    }

    private fun reloadData() {
        ioThread {
            ui.mAdapter.pageNumber = 0
            ui.mAdapter.setItems(ArrayList(db.allNotesOrderByDateLimitSkip(10, 0)))
        }
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deletePublisher.onNext((viewHolder as NoteViewHolder).note)
            }
        }).attachToRecyclerView(ui.mRecycler)
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