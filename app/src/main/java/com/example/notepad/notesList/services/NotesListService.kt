package com.example.notepad.notesList.services

import android.content.Context
import com.example.notepad.db.NoteRepository
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import io.reactivex.Observable

class NotesListService(context: Context) {
    private val repository by lazy { NoteRepository(context) }

    fun deleteNote(
        id: Int,
        note: Note
    ): Observable<NoteOperationResult> {
        return repository.deleteNote(id, note).map {
            if (it.error.isEmpty())
                return@map NoteOperationResult.Completed(it.id)
            else
                return@map NoteOperationResult.Failed(it.error)
        }
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return repository.updateNote(note).map {
            if (it.error.isEmpty())
                return@map NoteOperationResult.Completed(it.note!!.id, it.note)
            else
                return@map NoteOperationResult.Failed(it.error)
        }
    }

    fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return repository.loadNotes(filter, limit, skip).map {
            if (it.error.isEmpty())
                return@map NotesListOperationResult.Completed(it.notes)
            else
                return@map NotesListOperationResult.Failed(it.error)
        }
    }

    fun loadNotes(limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return repository.loadNotes(limit, skip).map {
            if (it.error.isEmpty())
                return@map NotesListOperationResult.Completed(it.notes)
            else
                return@map NotesListOperationResult.Failed(it.error)
        }
    }
}