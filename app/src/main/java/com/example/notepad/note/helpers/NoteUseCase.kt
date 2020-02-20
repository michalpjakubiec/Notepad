package com.example.notepad.note.helpers

import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable

class NoteUseCase(private val helper: NoteHelper) {

    fun saveNote(note: Note): Observable<NoteLoadSaveResult> {
        return if (note.id < 1)
            helper.saveNote(note)
        else
            helper.updateNote(note)
    }

    fun getNote(id: Int): Observable<NoteLoadSaveResult> {
        return if (id < 1)
            Observable
                .just(NoteLoadSaveResult.Completed(Note()) as NoteLoadSaveResult)
        else
            helper.getNote(id)
    }

    fun updateNoteDetails(note: Note): Observable<NoteOperationResult> {
        return helper.validateNoteDetails(note)
    }
}