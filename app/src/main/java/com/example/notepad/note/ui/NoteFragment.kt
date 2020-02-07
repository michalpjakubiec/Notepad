package com.example.notepad.note.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.notepad.R
import com.example.notepad.db.models.Note
import com.example.notepad.main.MainActivity
import com.example.notepad.note.mvi.NotePresenter
import com.example.notepad.note.mvi.NoteView
import com.example.notepad.note.mvi.NoteViewState
import com.example.notepad.notesList.ui.NotesListFragment
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.processors.PublishProcessor
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onMenuItemClick
import org.jetbrains.anko.support.v4.toast
import java.util.*

class NoteFragment : MviFragment<NoteView, NotePresenter>(), NoteView {
    private lateinit var ui: NoteFragmentUI<NoteFragment>
    private lateinit var mainActivity: MainActivity
    private val saveProcessor: PublishProcessor<Note> = PublishProcessor.create()

    override val saveIntent: Observable<Note>
        get() = saveProcessor.toObservable()
    override val validationIntent: Observable<String>
        get() = ui.etTitle.textChanges().map { it.toString().trim() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ui.toolbar.onMenuItemClick {
            saveProcessor.onNext(
                Note(
                    0,
                    Date().time,
                    ui.etTitle.text.toString(),
                    ui.etContent.text.toString(),
                    false
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = NoteFragmentUI()
        val layout = ui.createView(AnkoContext.create(requireContext(), this))
        mainActivity.setSupportActionBar(layout.findViewById(NoteFragmentUI.toolbarId))

        return layout
    }

    override fun createPresenter(): NotePresenter = NotePresenter(context!!)

    override fun render(state: NoteViewState) {
        if (state.isSavingFailed)
            toast(state.error)
        if (state.isValidationFailed)
            ui.etTitle.error = state.error
        if (state.isValidationCompleted)
            ui.etTitle.error = null
        if (state.isSavingCompleted) {
            toast(context!!.getString(R.string.notesSavedToast))
            mainActivity.replaceFragment(NotesListFragment())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity)
            mainActivity = context
    }
}