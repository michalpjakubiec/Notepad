package com.example.notepad.note.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.components.note.NoteFragmentUI
import com.example.notepad.db.models.Note
import com.example.notepad.main.ui.MainActivity
import com.example.notepad.note.mvi.NotePresenter
import com.example.notepad.note.mvi.NoteView
import com.example.notepad.utils.sameContent
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.kodein.di.KodeinAware
import org.kodein.di.LateInitKodein
import org.kodein.di.generic.instance

abstract class NoteFragmentBase : MviFragment<NoteView, NotePresenter>(), NoteView, KodeinAware {
    override val kodein = LateInitKodein()
    private val presenter: NotePresenter by instance()
    lateinit var ui: NoteFragmentUI
    lateinit var mainActivity: MainActivity
    var note: Note = Note()

    val loadSubject: PublishSubject<Int> = PublishSubject.create()
    override val loadIntent: Observable<Int> = loadSubject
    override val saveIntent: Observable<Note>
        get() = ui.saveMenuItem.clicks().map { note }
    override val updateIntent: Observable<Note>
        get() = Observable.merge(
            ui.etContent.textChanges()
                .distinct().map {
                    this.note.content = it.toString()
                    this.note
                },

            ui.etTitle.textChanges()
                .distinct().map {
                    this.note.title = it.toString()
                    this.note
                },

            ui.favouriteMenuItem.clicks()
                .map {
                    this.note.isFavourite = !this.note.isFavourite
                    this.note
                }
        ).distinctUntilChanged { t1, t2 -> !t1.sameContent(t2) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = NoteFragmentUI(context!!)
        mainActivity.setSupportActionBar(ui.findViewById(ui.toolbar.id))
        return ui
    }

    override fun createPresenter(): NotePresenter = presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
            this.kodein.baseKodein = (mainActivity as KodeinAware).kodein
        }
    }
}