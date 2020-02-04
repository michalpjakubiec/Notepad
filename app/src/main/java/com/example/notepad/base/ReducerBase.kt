package com.example.notepad.base

interface ReducerBase<T : ViewStateBase, T2 : ViewStateChangeBase> {
    fun reduce(state: T, change: T2): T
}