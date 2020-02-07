package com.example.notepad.note.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepad.note.mvi.NotePresenter
import com.example.notepad.note.mvi.NoteView
import com.example.notepad.note.mvi.NoteViewState
import com.hannesdorfmann.mosby3.mvi.MviFragment
import org.jetbrains.anko.AnkoContext

class NoteFragment : MviFragment<NoteView, NotePresenter>(), NoteView {
    private lateinit var ui: NoteFragmentUI<NoteFragment>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = NoteFragmentUI()
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun createPresenter(): NotePresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(state: NoteViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}