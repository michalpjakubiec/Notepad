package com.example.notepad.note.mvi

import com.example.notepad.base.ReducerBase
import com.example.notepad.note.utils.NoteOperationResult

class NoteReducer : ReducerBase<NoteViewState, NoteViewStateChange> {
    override fun reduce(state: NoteViewState, change: NoteViewStateChange): NoteViewState {
        val currentState = state.copy()
        when (change) {
            is NoteViewStateChange.SaveChange -> {
                when (change.saveResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult = NoteOperationResult.Completed
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = true
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.saveResult.error)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                    }
                }
            }
            is NoteViewStateChange.ValidationChange -> {
                when (change.validationResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult = NoteOperationResult.Completed
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.validationResult.error)
                        currentState.showValidationError = true
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                    }
                }
            }

            is NoteViewStateChange.FavouriteChange -> {
                when (change.favouriteResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult = NoteOperationResult.Completed
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = true
                        currentState.finishActivity = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.favouriteResult.error)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                    }
                }
            }

        }

        return currentState
    }
}