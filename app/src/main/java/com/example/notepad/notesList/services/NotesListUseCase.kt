package com.example.notepad.notesList.services

import com.example.notepad.db.NoteRepository
import com.example.notepad.notesList.utils.NotesListSearchResult
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class NotesListUseCase {

    private val comparator by lazy { NotesTitleComparator() }

    fun searchNotes(
        query: String,
        noteRepository: NoteRepository
    ): Observable<NotesListSearchResult> {
        return comparator.compareTitles(query, noteRepository).delay(2, TimeUnit.SECONDS)
    }
}