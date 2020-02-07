package com.example.notepad.note.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView

interface NoteView : MvpView {


    fun render(state: NoteViewState)
}