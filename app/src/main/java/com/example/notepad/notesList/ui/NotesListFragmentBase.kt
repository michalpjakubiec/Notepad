package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.main.MainActivity
import com.example.notepad.note.ui.NoteFragment
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListView
import com.example.notepad.notesList.mvi.NotesListViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext

abstract class NotesListFragmentBase : MviFragment<NotesListView, NotesListPresenter>(), NotesListView {
    lateinit var ui: NotesListFragmentUI<NotesListFragmentBase>
    lateinit var mainActivity: MainActivity
    val nextPageSubject: PublishSubject<Pair<String, Int>> = PublishSubject.create()
    val deleteSubject: PublishSubject<Note> = PublishSubject.create()
    val addSubject: PublishSubject<Unit> = PublishSubject.create()

    override val searchIntent: Observable<String>
        get() = ui.mEtSearch.textChanges().map { it.toString().trim() }
    override val nextPageIntent: Observable<Pair<String, Int>>
        get() = nextPageSubject
    override val deleteIntent: Observable<Note>
        get() = deleteSubject
    override val addIntent: Observable<Unit>
        get() = addSubject
    override val updateIntent: Observable<Note>
        get() = ui.mAdapter.updateItemSubject
    override val initialLoadIntent: Observable<Unit>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun createPresenter(): NotesListPresenter = NotesListPresenter(context!!)

    override fun render(state: NotesListViewState) {

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