package com.example.notepad.notesList.services

import com.example.notepad.db.Repository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListArchiveResult
import com.example.notepad.notesList.utils.NotesListSearchResult
import io.reactivex.Observable

class NoteArchiver {
    fun archiveNote(note: Note, noteRepository: Repository<Note>?): Observable<NotesListArchiveResult> {
        noteRepository?: return Observable.just(NotesListArchiveResult.Error("Unable to access database!"))

        if (note.isArchival == true)
            return Observable.just(NotesListArchiveResult.Error("Selected item already is in archive!"))

        note.isArchival = true
        noteRepository.updateItem(note)
        return Observable.just(NotesListArchiveResult.Completed(note))
    }
}