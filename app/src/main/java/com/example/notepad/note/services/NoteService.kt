package com.example.notepad.note.services

import android.content.Context
import com.example.notepad.db.NoteRepository
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteLoadSaveResult
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable

class NoteService(context: Context) {

    private val repository by lazy { NoteRepository(context) }

    fun validateNoteDetails(note: Note): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            if (note.title.isNullOrEmpty())
                throw Exception("Title must not be blank!")
            if (!note.title!!.first().isUpperCase())
                throw Exception("Title must begin with upper case letter!")

            NoteOperationResult.Completed(note) as NoteOperationResult
        }.onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
    }

    fun getNote(id: Int): Observable<NoteLoadSaveResult> {
        return repository.getNote(id).map {
            if (it.error.isEmpty())
                return@map NoteLoadSaveResult.Completed(it.note)
            else
                return@map NoteLoadSaveResult.Failed(it.error)
        }
    }

    fun updateNote(
        note: Note
    ): Observable<NoteLoadSaveResult> {
        return repository.updateNote(note).map {
            if (it.error.isEmpty())
                return@map NoteLoadSaveResult.Completed(it.note)
            else
                return@map NoteLoadSaveResult.Failed(it.error)
        }
    }

    fun saveNote(note: Note): Observable<NoteLoadSaveResult> {
        return repository.saveNote(note).map {
            if (it.error.isEmpty())
                return@map NoteLoadSaveResult.Completed(it.note)
            else
                return@map NoteLoadSaveResult.Failed(it.error)
        }
    }
}