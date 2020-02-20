package com.example.notepad.db.services.query

import com.example.notepad.db.services.responses.NoteIOResponse
import com.example.notepad.db.services.responses.NotesListIOResponse
import io.reactivex.Observable

interface QueryNoteService {
    fun getNote(id: Int): Observable<NoteIOResponse>

    fun loadNotes(limit: Int, skip: Int = 0, filter: String = ""): Observable<NotesListIOResponse>
}