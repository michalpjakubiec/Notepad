package com.example.notepad.main.utils

import androidx.fragment.app.Fragment

sealed class MainRedirectionResult {
    object NotStarted : MainRedirectionResult()
    object Pending : MainRedirectionResult()
    data class Completed(val result: Fragment?) : MainRedirectionResult()
    data class Failed(val error: String?) : MainRedirectionResult()
}