package com.example.notepad.note.helpers

import com.example.notepad.db.models.Note
import com.example.notepad.db.services.command.CommandNoteService
import com.example.notepad.db.services.query.QueryNoteService
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable

class NoteHelper(
    private val queryService: QueryNoteService,
    private val commandService: CommandNoteService
) {

    fun validateNoteDetails(note: Note): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            if (note.title.isEmpty())
                throw Exception("Title must not be blank!")
            if (!note.title.first().isUpperCase())
                throw Exception("Title must begin with upper case letter!")

            NoteOperationResult.Completed(note) as NoteOperationResult
        }.onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
    }

    fun getNote(id: Int): Observable<NoteLoadSaveResult> {
        return queryService.getNote(id).map {
            if (it.error.isEmpty())
                NoteLoadSaveResult.Completed(it.note)
            else
                NoteLoadSaveResult.Failed(it.error)
        }
    }

    fun updateNote(
        note: Note
    ): Observable<NoteLoadSaveResult> {
        return commandService.updateNote(note).map {
            if (it.error.isEmpty())
                NoteLoadSaveResult.Completed(it.note)
            else
                NoteLoadSaveResult.Failed(it.error)
        }
    }

    fun saveNote(note: Note): Observable<NoteLoadSaveResult> {
        return commandService.saveNote(note).map {
            if (it.error.isEmpty())
                NoteLoadSaveResult.Completed(it.note)
            else
                NoteLoadSaveResult.Failed(it.error)
        }
    }
}