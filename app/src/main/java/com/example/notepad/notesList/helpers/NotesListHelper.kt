package com.example.notepad.notesList.helpers

import com.example.notepad.db.models.Note
import com.example.notepad.db.services.command.CommandNoteService
import com.example.notepad.db.services.query.QueryNoteService
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import io.reactivex.Observable

class NotesListHelper(
    private val queryService: QueryNoteService,
    private val commandService: CommandNoteService
) {

    fun deleteNote(
        id: Int,
        note: Note
    ): Observable<NoteOperationResult> {
        return commandService.deleteNote(id, note).map {
            if (it.error.isEmpty())
                NoteOperationResult.Completed(it.id)
            else
                NoteOperationResult.Failed(it.error)
        }
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return commandService.updateNote(note).map {
            if (it.error.isEmpty())
                NoteOperationResult.Completed(it.note!!.id, it.note)
            else
                NoteOperationResult.Failed(it.error)
        }
    }

    fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListOperationResult> {
        if (filter.isNotEmpty() || filter.length < 3)
            return Observable.just(NotesListOperationResult.Failed("Query must be longer than 2 characters!"))

        return queryService.loadNotes(limit, skip, filter).map {
            if (it.error.isEmpty())
                NotesListOperationResult.Completed(it.notes)
            else
                NotesListOperationResult.Failed(it.error)
        }
    }
}