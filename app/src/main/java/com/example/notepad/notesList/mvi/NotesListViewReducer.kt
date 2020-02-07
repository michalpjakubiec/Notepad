package com.example.notepad.notesList.mvi

import android.util.Log
import com.example.notepad.base.ReducerBase
import com.example.notepad.notesList.utils.NotesListAddNoteResult
import com.example.notepad.notesList.utils.NotesListDeleteNoteResult
import com.example.notepad.notesList.utils.NotesListNextPageResult
import com.example.notepad.notesList.utils.NotesListSearchResult

class NotesListViewReducer : ReducerBase<NotesListViewState, NotesListViewStateChange> {

    override fun reduce(
        state: NotesListViewState,
        change: NotesListViewStateChange
    ): NotesListViewState {
        val currentState = state.copy()
        Log.i("change", change.toString())

        when (change) {
            is NotesListViewStateChange.NotesListChanged -> {
                when (change.searchResult) {
                    is NotesListSearchResult.Canceled -> {
                        currentState.isSearchCanceled = true
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.isSearchPending = false
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = false
                        currentState.notesList = ArrayList()
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = ""
                    }

                    is NotesListSearchResult.Completed -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = true
                        currentState.isSearchPending = false
                        currentState.isSearchFailed = false
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = false
                        currentState.notesList = change.searchResult.notesList
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = ""
                    }

                    is NotesListSearchResult.Error -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchPending = false
                        currentState.isSearchFailed = true
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = false
                        currentState.notesList = ArrayList()
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = change.searchResult.error
                    }
                    is NotesListSearchResult.Pending -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.isSearchPending = true
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = false
                        currentState.notesList = ArrayList()
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = ""
                    }
                }
            }

            is NotesListViewStateChange.NotesPageChanged -> {
                when (change.nextPageResult) {
                    is NotesListNextPageResult.Completed -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.isSearchPending = false
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = true
                        currentState.isNextPageFailed = false
                        currentState.notesList = change.nextPageResult.notesList
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = ""
                    }

                    is NotesListNextPageResult.Error -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.isSearchPending = false
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = true
                        currentState.notesList = ArrayList()
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = change.nextPageResult.error
                    }
                    is NotesListNextPageResult.Pending -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.isSearchPending = false
                        currentState.isNextPagePending = true
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = false
                        currentState.notesList = ArrayList()
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = false
                        currentState.deletedNoteId = -1
                        currentState.error = ""
                    }
                }
            }

            is NotesListViewStateChange.NoteDeleted -> {
                if (change.deleteNoteResult is NotesListDeleteNoteResult.Completed) {
                    currentState.isSearchCanceled = false
                    currentState.isSearchCompleted = false
                    currentState.isSearchFailed = false
                    currentState.isSearchPending = false
                    currentState.isNextPagePending = false
                    currentState.isNextPageCompleted = false
                    currentState.isNextPageFailed = false
                    currentState.notesList = ArrayList()
                    currentState.isDeleteCompleted = true
                    currentState.isAddingCompleted = false
                    currentState.deletedNoteId = change.deleteNoteResult.id
                    currentState.error = ""
                }
            }

            is NotesListViewStateChange.NoteAdd -> {
                when (change.addNoteResult) {
                    is NotesListAddNoteResult.Completed -> {
                        currentState.isSearchCanceled = false
                        currentState.isSearchCompleted = false
                        currentState.isSearchFailed = false
                        currentState.isSearchPending = false
                        currentState.isNextPagePending = false
                        currentState.isNextPageCompleted = false
                        currentState.isNextPageFailed = false
                        currentState.notesList = ArrayList()
                        currentState.isDeleteCompleted = false
                        currentState.isAddingCompleted = true
                        currentState.deletedNoteId = -1
                        currentState.error = ""
                    }
                }

            }
        }
        return currentState
    }
}