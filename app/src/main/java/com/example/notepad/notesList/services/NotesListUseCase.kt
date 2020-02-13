package com.example.notepad.notesList.services

import android.content.Context
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.*
import io.reactivex.Observable

class NotesListUseCase(context: Context) {

    private val service by lazy { NotesListService(context) }

    fun loadNextPage(filter: String): Observable<NotesListOperationResult> = loadNextPage(filter, 0)
    fun loadNextPage(skip: Int): Observable<NotesListOperationResult> =
        loadNextPage("", skip)

    fun loadNextPage(filter: String, skip: Int): Observable<NotesListOperationResult> {
        return if (filter.isEmpty())
            service.loadNotes(10, skip)
        else
            service.loadNotes(filter, 10, skip)
    }

    fun deleteNote(note: Note): Observable<NoteOperationResult> {
        return service.deleteNote(note.id, note)
    }

    fun addNote(): Observable<NoteOperationResult> {
        return Observable.just(NoteOperationResult.Completed(-1))
    }

    fun updateNote(note: Note): Observable<NoteOperationResult> {
        return service.updateNote(note)
    }
}