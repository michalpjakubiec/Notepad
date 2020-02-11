package com.example.notepad.note.mvi

import com.example.notepad.base.ReducerBase

class NoteReducer : ReducerBase<NoteViewState, NoteViewStateChange> {
    override fun reduce(state: NoteViewState, change: NoteViewStateChange): NoteViewState {
        val currentState = state.copy()
        when (change) {
            is NoteViewStateChange.SaveChange -> {
                when (change.saveResult) {
                    is NoteSaveResult.Completed -> {
                        currentState.isSavingCompleted = true
                        currentState.isSavingFailed = false
                        currentState.isValidationCompleted = false
                        currentState.isValidationFailed = false
                        currentState.isValidationCanceled = false
                        currentState.error = ""
                    }

                    is NoteSaveResult.Failed -> {
                        currentState.isSavingCompleted = false
                        currentState.isSavingFailed = true
                        currentState.isValidationCompleted = false
                        currentState.isValidationFailed = false
                        currentState.isValidationCanceled = false
                        currentState.error = change.saveResult.error
                    }
                }
            }
            is NoteViewStateChange.ValidationChange -> {
                when (change.validationResult) {
                    is NoteValidationResult.Completed -> {
                        currentState.isSavingCompleted = false
                        currentState.isSavingFailed = false
                        currentState.isValidationCompleted = true
                        currentState.isValidationFailed = false
                        currentState.isValidationCanceled = false
                        currentState.error = ""
                    }

                    is NoteValidationResult.Failed -> {
                        currentState.isSavingCompleted = false
                        currentState.isSavingFailed = false
                        currentState.isValidationCompleted = false
                        currentState.isValidationFailed = true
                        currentState.isValidationCanceled = false
                        currentState.error = change.validationResult.error
                    }
                }
            }
        }

        return currentState
    }
}