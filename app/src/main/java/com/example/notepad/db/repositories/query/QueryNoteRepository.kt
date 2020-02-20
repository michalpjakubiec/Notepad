package com.example.notepad.db.repositories.query

import com.example.notepad.db.models.Note
import io.reactivex.Maybe
import io.reactivex.Observable

interface QueryNoteRepository {
    fun getById(id: Int): Maybe<Note>

    fun getAll(query: String, take: Int, skip: Int): Observable<List<Note>>

    fun getAll(limit: Int, skip: Int): Observable<List<Note>>

    fun getAll(limit: Int): Observable<List<Note>>

    fun getAll(): Observable<List<Note>>
}