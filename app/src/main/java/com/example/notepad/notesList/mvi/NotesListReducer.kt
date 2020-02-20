package com.example.notepad.notesList.mvi

import android.util.Log
import com.example.notepad.base.ReducerBase
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult

class NotesListReducer : ReducerBase<NotesListViewState, NotesListViewStateChange> {

    override fun reduce(
        state: NotesListViewState,
        change: NotesListViewStateChange
    ): NotesListViewState {
        val currentState = state.copy()
        Log.i("change", change.toString())
        when (change) {
            is NotesListViewStateChange.DataSetChanged -> {
                when (change.filterResult) {
                    is NotesListOperationResult.NotStarted -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NotesListOperationResult.Pending -> {
                        currentState.notesListOperationResult = NotesListOperationResult.Pending
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NotesListOperationResult.Completed -> {
                        currentState.notesListOperationResult =
                            NotesListOperationResult.Completed(change.filterResult.result)
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = true
                    }

                    is NotesListOperationResult.Failed -> {
                        currentState.notesListOperationResult =
                            NotesListOperationResult.Failed(change.filterResult.error)
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = true
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                }
            }

            is NotesListViewStateChange.PageChanged -> {
                when (change.nextPageResult) {
                    is NotesListOperationResult.NotStarted -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NotesListOperationResult.Pending -> {
                        currentState.notesListOperationResult = NotesListOperationResult.Pending
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NotesListOperationResult.Completed -> {
                        currentState.notesListOperationResult =
                            NotesListOperationResult.Completed(change.nextPageResult.result)
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NotesListOperationResult.Failed -> {
                        currentState.notesListOperationResult =
                            NotesListOperationResult.Failed(change.nextPageResult.error)
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                }
            }

            is NotesListViewStateChange.NoteDeleted -> {
                when (change.deleteNoteResult) {
                    is NoteOperationResult.NotStarted -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Pending -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.Pending
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Completed -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.deleteNoteResult.id)
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = true
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.deleteNoteResult.error)
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                }
            }

            is NotesListViewStateChange.noteShowed -> {
                when (change.showNoteResult) {
                    is NoteOperationResult.NotStarted -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                    is NoteOperationResult.Pending -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.Pending
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Completed -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.showNoteResult.id)
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = true
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.showNoteResult.error)
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                }
            }

            is NotesListViewStateChange.NoteUpdated -> {
                when (change.updateNoteResult) {
                    is NoteOperationResult.NotStarted -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.NotStarted
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                    is NoteOperationResult.Pending -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult = NoteOperationResult.Pending
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Completed -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(
                                change.updateNoteResult.id,
                                change.updateNoteResult.note
                            )
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.notesListOperationResult = NotesListOperationResult.NotStarted
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.updateNoteResult.error)
                        currentState.showFilterBarError = false
                        currentState.redirectToNoteFragment = false
                        currentState.deleteChangedNoteFromView = false
                        currentState.replaceItemsInAdapter = false
                    }
                }
            }
        }
        return currentState
    }
}