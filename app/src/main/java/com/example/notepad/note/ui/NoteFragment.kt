package com.example.notepad.note.ui

import android.os.Bundle
import com.example.notepad.R
import com.example.notepad.db.models.Note
import com.example.notepad.note.mvi.NoteViewState
import com.example.notepad.notesList.ui.NotesListFragment
import org.jetbrains.anko.support.v4.toast
import java.util.*

class NoteFragment : NoteFragmentBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note(0, Date().time, "", "", isArchival = false, isFavourite = false)
    }

    override fun render(state: NoteViewState) {
        if (state.isSavingFailed)
            toast(state.error)
        if (state.isValidationFailed)
            ui.etTitle.error = state.error
        if (state.isValidationCompleted)
            ui.etTitle.error = null
        if (state.isSavingCompleted) {
            toast(context!!.getString(R.string.notesSavedToast))
            mainActivity.replaceFragment(
                NotesListFragment(),
                (NoteFragment::class.simpleName + NotesListFragment::class.simpleName)
            )
        }
    }
}