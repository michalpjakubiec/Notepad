package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.note.ui.NoteFragment
import com.example.notepad.notesList.adapter.NoteViewHolder
import com.example.notepad.notesList.mvi.NotesListViewState
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import org.jetbrains.anko.design.snackbar

class NotesListFragment : NotesListFragmentBase() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeToDelete()
        initialLoadSubject.onNext(Unit)
    }

    override fun render(state: NotesListViewState) {
        when (state.notesListOperationResult) {
            is NotesListOperationResult.Pending -> listChangePendingState()
            is NotesListOperationResult.Failed -> listChangeFailedState(state)
            is NotesListOperationResult.Completed -> listChangeCompletedState(state)
        }

        when (state.noteOperationResult) {
            is NoteOperationResult.Completed -> itemChangeCompletedState(state)
            is NoteOperationResult.Failed -> itemChangeFailedState(state)
        }
    }

    private fun listChangePendingState() {
        ui.swipeRefreshLayout.isRefreshing = true
        ui.mEtSearch.error = null
    }

    private fun listChangeFailedState(state: NotesListViewState) {
        ui.swipeRefreshLayout.isRefreshing = false

        val error = (state.notesListOperationResult as NotesListOperationResult.Failed).error
        if (state.showFilterBarError)
            ui.mEtSearch.error = error
        else
            ui.mainLayout.snackbar(error)
    }

    private fun listChangeCompletedState(state: NotesListViewState) {
        ui.swipeRefreshLayout.isRefreshing = false

        val items = (state.notesListOperationResult as NotesListOperationResult.Completed).result
        if (state.replaceItemsInAdapter)
            ui.mAdapter.setItems(items)
        else
            ui.mAdapter.addItems(items)
    }

    private fun itemChangeFailedState(state: NotesListViewState) {
        ui.mainLayout.snackbar((state.noteOperationResult as NoteOperationResult.Failed).error)

    }

    private fun itemChangeCompletedState(state: NotesListViewState) {
        if (state.redirectToNoteFragment)
            mainActivity.replaceFragment(NoteFragment())

        val itemId = (state.noteOperationResult as NoteOperationResult.Completed).id
        if (state.deleteChangedNoteFromView)
            ui.mAdapter.deletedItem(itemId)
        else
            ui.mAdapter.updateItem(itemId)
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
                deleteSubject.onNext((viewHolder as NoteViewHolder).note)
            }
        }).attachToRecyclerView(ui.mRecycler)
    }
}