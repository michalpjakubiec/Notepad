package com.example.notepad.main.ui

import com.example.notepad.main.mvi.MainViewState
import com.example.notepad.main.utils.MainRedirectionResult
import org.jetbrains.anko.design.snackbar

class MainActivity : MainActivityBase() {

    override fun render(viewState: MainViewState) {
        when (viewState.redirectionResult) {
            is MainRedirectionResult.Completed -> handleRedirectionStateCompleted(viewState)
            is MainRedirectionResult.Failed -> handleRedirectionStateFailed(viewState)
        }
    }

    private fun handleRedirectionStateCompleted(state: MainViewState) {
        replaceFragment(
            mainContainer.id,
            (state.redirectionResult as MainRedirectionResult.Completed).result!!
        )
    }

    private fun handleRedirectionStateFailed(state: MainViewState) {
        mainContainer.snackbar((state.redirectionResult as MainRedirectionResult.Failed).error!!)
    }
}
