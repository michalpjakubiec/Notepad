package com.example.notepad.note.services

import android.content.Context
import com.example.notepad.db.NoteRepository
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable

class NoteService(context: Context) {

    private val repository by lazy { NoteRepository(context) }

    fun changeFavouriteStatus(note: Note): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            note.isFavourite = !note.isFavourite
            NoteOperationResult.Completed(note) as NoteOperationResult
        }.onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
    }

    fun validateNote(title: String): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            if (title.isEmpty())
                throw Exception("Title must not be blank!")
            if (!title.first().isUpperCase())
                throw Exception("Title must begin with upper case letter!")

            NoteOperationResult.Completed(null) as NoteOperationResult
        }.onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
    }

    fun getNote(id: Int): Observable<NoteOperationResult> {
        return repository.getNote(id).map {
            if (it.error.isEmpty())
                return@map NoteOperationResult.Completed(it.note)
            else
                return@map NoteOperationResult.Failed(it.error)
        }
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return repository.updateNote(note).map {
            if (it.error.isEmpty())
                return@map NoteOperationResult.Completed(it.note)
            else
                return@map NoteOperationResult.Failed(it.error)
        }
    }

    fun saveNote(note: Note): Observable<NoteOperationResult> {
        return repository.saveNote(note).map {
            if (it.error.isEmpty())
                return@map NoteOperationResult.Completed(it.note)
            else
                return@map NoteOperationResult.Failed(it.error)
        }
    }
}