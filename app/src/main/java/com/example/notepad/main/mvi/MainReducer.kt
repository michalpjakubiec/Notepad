package com.example.notepad.main.mvi

import com.example.notepad.base.ReducerBase
import com.example.notepad.main.utils.MainRedirectionResult

class MainReducer : ReducerBase<MainViewState, MainViewStateChange> {
    override fun reduce(state: MainViewState, change: MainViewStateChange): MainViewState {
        val currentState = state.copy()

        when (change) {
            is MainViewStateChange.FragmentChanged -> {
                when (change.redirectionResult) {
                    is MainRedirectionResult.Completed -> {
                        currentState.redirectionResult =
                            MainRedirectionResult.Completed(change.redirectionResult.result)
                        currentState.fragmentShowed = false
                    }

                    is MainRedirectionResult.Failed -> {
                        currentState.redirectionResult =
                            MainRedirectionResult.Failed(change.redirectionResult.error)
                        currentState.fragmentShowed = false
                    }
                }
            }
        }

        return currentState
    }
}