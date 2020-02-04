package com.example.notepad.notesList.services

import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListSearchResult
import io.reactivex.Observable

class NotesListUseCase {

    private val comparator by lazy { NotesTitleComparator() }

    fun searchNotes(query: String, notes: ArrayList<Note>): Observable<NotesListSearchResult> {
        return comparator.compareTitles(notes, query)
    }
}