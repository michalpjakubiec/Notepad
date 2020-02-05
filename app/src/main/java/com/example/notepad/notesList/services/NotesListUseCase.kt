package com.example.notepad.notesList.services

import com.example.notepad.db.Repository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListArchiveResult
import com.example.notepad.notesList.utils.NotesListSearchResult
import io.reactivex.Observable

class NotesListUseCase {

    private val comparator by lazy { NotesTitleComparator() }
    private val archiver by lazy { NoteArchiver() }

    fun searchNotes(query: String, noteRepository: Repository<Note>): Observable<NotesListSearchResult> {
        return comparator.compareTitles(query, noteRepository)
    }

    fun archiveNote(note: Note, noteRepository:Repository<Note>): Observable<NotesListArchiveResult> {
        return archiver.archiveNote(note, noteRepository)
    }
}