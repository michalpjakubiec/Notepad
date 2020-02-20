package com.example.notepad.db.repositories.query

import com.example.notepad.db.models.Note
import com.example.notepad.db.source.local.NoteDao
import io.reactivex.Maybe
import io.reactivex.Observable

class QueryNoteRepositoryImpl(
    private val db: NoteDao
) : QueryNoteRepository {
    override fun getById(id: Int): Maybe<Note> {
        return db.getNoteById(id)
    }

    override fun getAll(query: String, take: Int, skip: Int): Observable<List<Note>> {
        return db.allNotesFilterByTitleOrderByDateLimitSkip(query, take, skip)
    }

    override fun getAll(limit: Int, skip: Int): Observable<List<Note>> {
        return db.allNotesOrderByDateLimitSkip(limit, skip)
    }

    override fun getAll(limit: Int): Observable<List<Note>> {
        return db.allNotesLimit(limit)
    }

    override fun getAll(): Observable<List<Note>> {
        return db.allNotes()
    }
}