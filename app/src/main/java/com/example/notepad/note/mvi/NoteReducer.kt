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
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.saveResult.result)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = true
                        currentState.updateTextEdits = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.saveResult.error)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                        currentState.updateTextEdits = false
                    }
                }
            }
            is NoteViewStateChange.ValidationChange -> {
                when (change.validationResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.validationResult.result)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                        currentState.updateTextEdits = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.validationResult.error)
                        currentState.showValidationError = true
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                        currentState.updateTextEdits = false
                    }
                }
            }

            is NoteViewStateChange.FavouriteChange -> {
                when (change.favouriteResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.favouriteResult.result)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = true
                        currentState.finishActivity = false
                        currentState.updateTextEdits = false
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.favouriteResult.error)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                        currentState.updateTextEdits = false
                    }
                }
            }

            is NoteViewStateChange.NoteChange -> {
                when (change.loadingResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.loadingResult.result)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = true
                        currentState.finishActivity = false
                        currentState.updateTextEdits = true
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.loadingResult.error)
                        currentState.showValidationError = false
                        currentState.changeFavouritesIcon = false
                        currentState.finishActivity = false
                        currentState.updateTextEdits = false
                    }
                }
            }
        }

        return currentState
    }
}