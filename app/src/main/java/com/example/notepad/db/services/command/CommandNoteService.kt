package com.example.notepad.db.services.command

import com.example.notepad.db.models.Note
import com.example.notepad.db.services.responses.DeleteNoteIOResponse
import com.example.notepad.db.services.responses.NoteIOResponse
import io.reactivex.Observable

interface CommandNoteService {
    fun saveNote(note: Note): Observable<NoteIOResponse>

    fun deleteNote(id: Int, note: Note): Observable<DeleteNoteIOResponse>

    fun updateNote(note: Note): Observable<NoteIOResponse>
}