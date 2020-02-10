package com.example.notepad.notesList.services

import android.content.Context
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import io.reactivex.Observable
import java.lang.Error
import java.lang.Exception

class NoteRepository(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }

    fun deleteNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return try {
            Observable.fromCallable {
                val id = note.id
                db.delete(note)
                NoteOperationResult.Completed(id)
            }
        } catch (ex: Exception) {
            Observable.just(NoteOperationResult.Failed(ex.toString()))
        }
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return try {
            Observable.fromCallable {
                db.update(note)
                NoteOperationResult.Completed(note.id)
            }
        } catch (ex: Exception) {
            Observable.just(NoteOperationResult.Failed(ex.toString()))
        }
    }

    fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return try {
            if (filter.isEmpty())
                return Observable.just(NotesListOperationResult.NotStarted)

            if (filter.length < 3)
                throw Error("Query must be longer than 2 characters")

            Observable.fromCallable {
                val items = db.allNotesFilterByTitleOrderByDateLimitSkip(filter, limit, skip)
                NotesListOperationResult.Completed(items)
            }

        } catch (ex: Exception) {
            Observable.just(NotesListOperationResult.Failed(ex.toString()))
        }
    }

    fun loadNotes(limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return try {
            Observable.fromCallable {
                val items = db.allNotesOrderByDateLimitSkip(limit, skip)
                NotesListOperationResult.Completed(items)
            }

        } catch (ex: Exception) {
            Observable.just(NotesListOperationResult.Failed(ex.toString()))
        }
    }
}