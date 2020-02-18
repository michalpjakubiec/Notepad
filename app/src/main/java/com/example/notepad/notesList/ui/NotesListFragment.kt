package com.example.notepad.notesList.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.base.HaveTag
import com.example.notepad.main.utils.ReplaceFragmentArguments
import com.example.notepad.notesList.adapter.NoteViewHolder
import com.example.notepad.notesList.mvi.NotesListViewState
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListFilterArguments
import com.example.notepad.notesList.utils.NotesListOperationResult
import com.example.notepad.utils.NOTES_LIST_FRAGMENT_TAG
import com.example.notepad.utils.tripleStageOnClick
import org.jetbrains.anko.design.snackbar

class NotesListFragment : NotesListFragmentBase(), HaveTag {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeToDelete()
        initFilters()
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
        ui.isNextPageLoading = false

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
        val itemId = (state.noteOperationResult as NoteOperationResult.Completed).id
        if (state.redirectToNoteFragment) {
            mainActivity.redirectSubject.onNext(
                ReplaceFragmentArguments(
                    itemId,
                    redirectToNoteFragment = true,
                    redirectToNotesListFragment = false
                )
            )
            return
        }
        if (state.deleteChangedNoteFromView)
            ui.mAdapter.deletedItem(itemId)
        else
            ui.mAdapter.updateItem(
                itemId,
                (state.noteOperationResult as NoteOperationResult.Completed).note!!
            )
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

    private fun initFilters() {
        val archIcons = listOf(
            context!!.getDrawable(R.drawable.ic_archive_black_24dp)!!,
            context!!.getDrawable(R.drawable.ic_archive_white_24dp)!!,
            context!!.getDrawable(R.drawable.ic_unarchive_white_24dp)!!
        )

        this.ui.filterArchival.setOnMenuItemClickListener {
            it.tripleStageOnClick(archIcons, 3)
            this.filterSubject.onNext(
                NotesListFilterArguments(
                    ((ui.filterFavourite as View).tag as Boolean),
                    ((it as View).tag as Boolean)
                )
            )
            true
        }

        val favIcons = listOf(
            context!!.getDrawable(R.drawable.ic_favorite_black_24dp)!!,
            context!!.getDrawable(R.drawable.ic_favorite_white_24dp)!!,
            context!!.getDrawable(R.drawable.ic_favorite_border_white_24dp)!!
        )

        this.ui.filterFavourite.setOnMenuItemClickListener {
            it.tripleStageOnClick(favIcons, 3)
            this.filterSubject.onNext(
                NotesListFilterArguments(
                    ((it as View).tag as Boolean),
                    ((ui.filterArchival as View).tag as Boolean)
                )
            )
            true
        }
    }

    override fun getFragmentTag(): String {
        return NOTES_LIST_FRAGMENT_TAG
    }
}