package com.example.notepad.main.mvi

import com.example.notepad.base.ViewStateChangeBase
import com.example.notepad.main.utils.MainRedirectionResult

sealed class MainViewStateChange : ViewStateChangeBase {
    data class FragmentChanged(val redirectionResult: MainRedirectionResult) : MainViewStateChange()
}