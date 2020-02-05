package com.example.notepad.notesList.services

import com.example.notepad.db.NoteRepository
import com.example.notepad.notesList.utils.NotesListSearchResult
import com.example.notepad.utils.filterByTitle
import io.reactivex.Observable

class NotesTitleComparator {
    fun compareTitles(query: String, noteRepository: NoteRepository): Observable<NotesListSearchResult> {
        val notes = noteRepository.getAll(NoteRepository.notesTableKey)

        if (query.isEmpty())
            return Observable.just(NotesListSearchResult.Canceled(ArrayList(notes)))
        if (query.length < 3)
            return Observable.just(NotesListSearchResult.Error("Query must be longer than 2 characters"))

        return Observable.just(NotesListSearchResult.Completed(ArrayList(notes.filterByTitle(query))))
    }
}