package com.example.notepad.notesList.services

import com.example.notepad.db.NoteDatabase
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
}