package com.example.notepad.note.ui

import android.os.Bundle
import com.example.notepad.R
import com.example.notepad.db.models.Note
import com.example.notepad.note.mvi.NoteViewState
import com.example.notepad.note.utils.NoteOperationResult
import com.example.notepad.notesList.ui.NotesListFragment
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast
import java.util.*

class NoteFragment : NoteFragmentBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note(0, Date().time, "", "", isArchival = false, isFavourite = false)
    }

    override fun render(state: NoteViewState) {
        when (state.noteOperationResult) {
            is NoteOperationResult.Failed -> noteOperationStateFailed(state)
            is NoteOperationResult.Completed -> noteOperationStateCompleted(state)
        }
    }

    private fun noteOperationStateCompleted(state: NoteViewState) {
        this.ui.etTitle.error = null

        if (state.changeFavouritesIcon)
            this.ui.favouriteMenuItem.icon =
                if (note.isFavourite) context!!.getDrawable(R.drawable.ic_favorite_white_24dp)
                else context!!.getDrawable(R.drawable.ic_favorite_border_white_24dp)

        if (state.finishActivity) {
            toast(context!!.getString(R.string.notesSavedToast))
            mainActivity.replaceFragment(
                NotesListFragment(),
                (NoteFragment::class.simpleName + NotesListFragment::class.simpleName)
            )
        }
    }

    private fun noteOperationStateFailed(state: NoteViewState) {
        val error = (state.noteOperationResult as NoteOperationResult.Failed).error
        if (state.showValidationError)
            this.ui.etTitle.error = error
        else
            this.ui.mainLayout.snackbar(error ?: "")

    }
}