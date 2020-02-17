package com.example.notepad.note.services

import android.content.Context
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable

class NoteUseCase(context: Context) {

    private val service by lazy { NoteService(context) }

    fun saveNote(note: Note): Observable<NoteLoadSaveResult> {
        return if (note.id < 1)
            service.saveNote(note)
        else
            service.updateNote(note)
    }

    fun getNote(id: Int): Observable<NoteLoadSaveResult> {
        return if (id < 1)
            service.createNote()
        else
            service.getNote(id)
    }

    fun updateNoteDetails(note: Note): Observable<NoteOperationResult> {
        return service.validateNoteDetails(note)
    }
}