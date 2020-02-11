package com.example.notepad.note.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.components.note.NoteFragmentUI
import com.example.notepad.db.models.Note
import com.example.notepad.main.MainActivity
import com.example.notepad.note.mvi.NotePresenter
import com.example.notepad.note.mvi.NoteView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable

abstract class NoteFragmentBase : MviFragment<NoteView, NotePresenter>(), NoteView {
    lateinit var ui: NoteFragmentUI
    lateinit var mainActivity: MainActivity
    lateinit var note: Note

    override val saveIntent: Observable<Note>
        get() = ui.saveMenuItem.clicks().map {
            note.title = ui.etTitle.text.toString()
            note.title = ui.etContent.text.toString()
            note
        }
    override val favouriteIntent: Observable<Note>
        get() = ui.saveMenuItem.clicks().map { note }

    override val validationIntent: Observable<String>
        get() = ui.etTitle.textChanges().map { it.toString().trim() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = NoteFragmentUI(context!!)
        mainActivity.setSupportActionBar(ui.findViewById(ui.toolbar.id))
        return ui
    }

    override fun createPresenter(): NotePresenter = NotePresenter(context!!)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity)
            mainActivity = context
    }
}