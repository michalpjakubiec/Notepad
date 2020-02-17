package com.example.notepad.main.mvi

import com.example.notepad.base.ViewStateBase
import com.example.notepad.main.utils.MainRedirectionResult

data class MainViewState(
    var redirectionResult: MainRedirectionResult = MainRedirectionResult.NotStarted
) : ViewStateBase