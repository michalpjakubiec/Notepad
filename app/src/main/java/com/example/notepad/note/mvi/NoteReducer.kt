package com.example.notepad.note.mvi

import com.example.notepad.base.ReducerBase
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult

class NoteReducer : ReducerBase<NoteViewState, NoteViewStateChange> {
    override fun reduce(state: NoteViewState, change: NoteViewStateChange): NoteViewState {
        val currentState = state.copy()
        when (change) {
            is NoteViewStateChange.SaveChange -> {
                when (change.saveResult) {
                    is NoteLoadSaveResult.Completed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.NotStarted
                        currentState.noteLoadSaveResult =
                            NoteLoadSaveResult.Completed(change.saveResult.result)
                        currentState.showValidationError = false
                        currentState.finishActivity = true
                        currentState.updateNote = false
                    }

                    is NoteLoadSaveResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.NotStarted
                        currentState.noteLoadSaveResult =
                            NoteLoadSaveResult.Failed(change.saveResult.error)
                        currentState.showValidationError = false
                        currentState.finishActivity = false
                        currentState.updateNote = false
                    }
                }
            }
            is NoteViewStateChange.NoteChange -> {
                when (change.loadResult) {
                    is NoteLoadSaveResult.Completed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.NotStarted
                        currentState.noteLoadSaveResult =
                            NoteLoadSaveResult.Completed(change.loadResult.result)
                        currentState.showValidationError = false
                        currentState.finishActivity = false
                        currentState.updateNote = true
                    }

                    is NoteLoadSaveResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.NotStarted
                        currentState.noteLoadSaveResult =
                            NoteLoadSaveResult.Failed(change.loadResult.error)
                        currentState.showValidationError = false
                        currentState.finishActivity = false
                        currentState.updateNote = false
                    }
                }
            }
            is NoteViewStateChange.NoteDetailsChanged -> {
                when (change.updateResult) {
                    is NoteOperationResult.Completed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Completed(change.updateResult.result)
                        currentState.noteLoadSaveResult = NoteLoadSaveResult.NotStarted
                        currentState.showValidationError = false
                        currentState.finishActivity = false
                        currentState.updateNote = true
                    }

                    is NoteOperationResult.Failed -> {
                        currentState.noteOperationResult =
                            NoteOperationResult.Failed(change.updateResult.error)
                        currentState.noteLoadSaveResult = NoteLoadSaveResult.NotStarted
                        currentState.showValidationError = true
                        currentState.finishActivity = false
                        currentState.updateNote = false
                    }
                }
            }
        }

        return currentState
    }
}
