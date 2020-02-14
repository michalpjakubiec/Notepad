package com.example.notepad.base

import androidx.fragment.app.Fragment

interface ReplaceFragment {
    var currentFragmentTag: String?

    fun replaceFragment(containerId: Int, fragment: Fragment)
}