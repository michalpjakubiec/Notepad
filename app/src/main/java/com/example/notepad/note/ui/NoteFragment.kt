package com.example.notepad.note.ui

import android.os.Bundle
import com.example.notepad.R
import com.example.notepad.base.HaveTag
import com.example.notepad.main.utils.ReplaceFragmentArguments
import com.example.notepad.note.mvi.NoteViewState
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult
import com.example.notepad.utils.NOTE_FRAGMENT_TAG
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.toast

class NoteFragment : NoteFragmentBase(), HaveTag {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()

        val id = this.arguments?.getInt("ID", -1) ?: -1
        if (this.note.id != id)
            loadSubject.onNext(id)
    }

    override fun render(state: NoteViewState) {
        if (isRestoringViewState) {
            refreshAll()
            return
        }
        when (state.noteOperationResult) {
            is NoteOperationResult.Failed -> noteOperationStateFailed(state)
            is NoteOperationResult.Completed -> noteOperationStateCompleted(state)
        }
        when (state.noteLoadSaveResult) {
            is NoteLoadSaveResult.Failed -> noteLoadSaveStateFailed(state)
            is NoteLoadSaveResult.Completed -> noteLoadSaveStateCompleted(state)
        }
    }

    private fun noteOperationStateCompleted(state: NoteViewState) {
        handleCompleted()

        if (state.updateNote)
            refreshFavIcon()
    }

    private fun noteOperationStateFailed(state: NoteViewState) {
        val error = (state.noteOperationResult as NoteOperationResult.Failed).error
        handleError(state, error)
    }

    private fun noteLoadSaveStateCompleted(state: NoteViewState) {
        handleCompleted()

        note = (state.noteLoadSaveResult as NoteLoadSaveResult.Completed).result!!
        refreshAll()

        if (state.finishActivity) {
            toast(context!!.getString(R.string.notesSavedToast))
            mainActivity.redirectSubject.onNext(
                ReplaceFragmentArguments(
                    -1,
                    redirectToNoteFragment = false,
                    redirectToNotesListFragment = true
                )
            )
        }
    }

    private fun noteLoadSaveStateFailed(state: NoteViewState) {
        val error = (state.noteLoadSaveResult as NoteLoadSaveResult.Failed).error
        handleError(state, error)
    }

    private fun handleError(state: NoteViewState, error: String?) {
        if (state.showValidationError)
            this.ui.etTitle.error = error
        else
            this.ui.mainLayout.snackbar(error ?: "")

        this.ui.saveMenuItem.isEnabled = false
        this.ui.saveMenuItem.icon = context!!.getDrawable(R.drawable.ic_save_black_24dp)
    }

    private fun handleCompleted() {
        this.ui.etTitle.error = null
        this.ui.saveMenuItem.isEnabled = true
        this.ui.saveMenuItem.icon = context!!.getDrawable(R.drawable.ic_save_white_24dp)
    }

    private fun refreshAll() {
        refreshFavIcon()
        refreshTextEdits()
    }

    private fun refreshFavIcon() {
        this.ui.favouriteMenuItem.icon =
            if (note.isFavourite) context!!.getDrawable(R.drawable.ic_favorite_white_24dp)
            else context!!.getDrawable(R.drawable.ic_favorite_border_white_24dp)
    }

    private fun refreshTextEdits() {
        this.ui.etTitle.setText(note.title)
        this.ui.etTitle.setSelection(note.title.length)
        this.ui.etContent.setText(note.content)
        this.ui.etContent.setSelection(note.content.length)
    }

    override fun getFragmentTag(): String {
        return NOTE_FRAGMENT_TAG
    }
}