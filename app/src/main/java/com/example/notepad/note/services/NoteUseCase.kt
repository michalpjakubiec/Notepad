package com.example.notepad.note.services

import android.content.Context
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable

class NoteUseCase(context: Context) {

    private val service by lazy { NoteService(context) }

    fun validateNote(title: String): Observable<NoteOperationResult> {
        return service.validateNote(title)
    }

    fun changeFavouriteStatus(note: Note): Observable<NoteOperationResult> {
        return service.changeFavouriteStatus(note)
    }

    fun saveNote(note: Note): Observable<NoteOperationResult> {
        return service.saveNote(note)
    }
}