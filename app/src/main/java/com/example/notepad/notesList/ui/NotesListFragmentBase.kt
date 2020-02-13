package com.example.notepad.notesList.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepad.components.notesList.NotesListFragmentUI
import com.example.notepad.db.models.Note
import com.example.notepad.main.MainActivity
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.recyclerview.scrollEvents
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class NotesListFragmentBase : MviFragment<NotesListView, NotesListPresenter>(),
    NotesListView {
    lateinit var ui: NotesListFragmentUI
    lateinit var mainActivity: MainActivity
    val deleteSubject: PublishSubject<Note> = PublishSubject.create()

    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().filter { ui.mEtSearch.isFocused && it.isNotEmpty() }
            .map { it.toString().trim() }
    override val nextPageIntent: Observable<Pair<String, Int>>
        get() = ui.mRecycler.scrollEvents()
            .distinctUntilChanged()
            .filter { it.dy > 0 && it.view.layoutManager != null && !it.view.hasPendingAdapterUpdates() }
            .filter {
                val manager = it.view.layoutManager as LinearLayoutManager
                val totalItemCount = manager.itemCount
                val lastVisibleItem = manager.findLastVisibleItemPosition()

                (!ui.isNextPageLoading) && totalItemCount <= lastVisibleItem + 2
            }
            .map {
                ui.isNextPageLoading = true
                val skipItems =
                    if (ui.mAdapter.notes.size < ui.mAdapter.pageNumber * 10) ui.mAdapter.notes.size else ui.mAdapter.pageNumber * 10

                Pair(ui.mEtSearch.text.toString(), skipItems)
            }

    override val deleteIntent: Observable<Note>
        get() = deleteSubject
    override val addIntent: Observable<Unit>
        get() = ui.fabAdd.clicks()
    override val updateIntent: Observable<Note>
        get() = ui.mAdapter.updateItemSubject
    override val initialLoadIntent: Observable<Unit>
        get() = Observable.just(Unit)
    override val refreshIntent: Observable<Unit>
        get() = ui.swipeRefreshLayout.refreshes().map {
            ui.mEtSearch.setText("")
        }

    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity)
            this.mainActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui =
            NotesListFragmentUI(context!!)
        return ui
    }
}