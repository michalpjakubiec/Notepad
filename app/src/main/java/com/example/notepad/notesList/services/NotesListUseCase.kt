package com.example.notepad.notesList.services

import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListDeleteNoteResult
import com.example.notepad.notesList.utils.NotesListNextPageResult
import com.example.notepad.notesList.utils.NotesListSearchResult
import io.reactivex.Observable

class NotesListUseCase {

    private val comparator by lazy { NotesTitleComparator() }
    private val loader by lazy { NotesPageLoader() }

    fun searchNotes(
        query: String,
        db: NoteDatabase
    ): Observable<NotesListSearchResult> {
        return comparator.compareTitles(query, db)
    }

    fun loadNextPage(
        page: Int,
        db: NoteDatabase
    ): Observable<NotesListNextPageResult> {
        return loader.loadPage(page, db)
    }

    fun deleteNote(note: Note, db: NoteDatabase): Observable<NotesListDeleteNoteResult> {
        val id = note.id
        db.noteDao().delete(note)
        return Observable.just(NotesListDeleteNoteResult.Completed(id))
    }
}