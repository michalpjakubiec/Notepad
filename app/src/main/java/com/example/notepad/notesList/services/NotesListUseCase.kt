package com.example.notepad.notesList.services

import android.content.Context
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.*
import io.reactivex.Observable

class NotesListUseCase(context: Context) {

    private val repository by lazy { NoteRepository(context) }

    fun loadNextPage(filter: String): Observable<NotesListOperationResult> = loadNextPage(filter, 0)
    fun loadNextPage(pageNumber: Int): Observable<NotesListOperationResult> =
        loadNextPage("", pageNumber)

    fun loadNextPage(filter: String, pageNumber: Int): Observable<NotesListOperationResult> {
        return if (filter.isEmpty())
            repository.loadNotes(10, pageNumber * 10)
        else
            repository.loadNotes(filter, 10, pageNumber * 10)
    }

    fun deleteNote(note: Note): Observable<NoteOperationResult> {
        return repository.deleteNote(note)
    }

    fun addNote(): Observable<NoteOperationResult> {
        return Observable.just(NoteOperationResult.Completed(-1))
    }

    fun updateNote(note: Note): Observable<NoteOperationResult> {
        return repository.updateNote(note)
    }
}