package com.example.notepad.notesList.services

import android.content.Context
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Error
import java.lang.Exception

class NotesListService(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }

    fun deleteNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            try {
                val id = note.id
                db.delete(note)
                NoteOperationResult.Completed(id)
            } catch (ex: Exception) {
                NoteOperationResult.Failed(ex.toString())
            }
        }.subscribeOn(Schedulers.io())
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            try {
                db.update(note)
                NoteOperationResult.Completed(note.id)
            } catch (ex: Exception) {
                NoteOperationResult.Failed(ex.toString())
            }
        }.subscribeOn(Schedulers.io())
    }

    fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return Observable.fromCallable {
            try {
                if (filter.isNotEmpty() && filter.length < 3)
                    return@fromCallable NotesListOperationResult.Failed("Query must be longer than 2 characters")


                val items: List<Note> = if (filter.isEmpty())
                    db.allNotesOrderByDateLimitSkip(limit, 0)
                else
                    db.allNotesFilterByTitleOrderByDateLimitSkip(filter, limit, skip)

                NotesListOperationResult.Completed(items)

            } catch (ex: Exception) {
                NotesListOperationResult.Failed(ex.toString())
            }
        }.subscribeOn(Schedulers.io())
    }

    fun loadNotes(limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return Observable.fromCallable {
            try {
                val items = db.allNotesOrderByDateLimitSkip(limit, skip)
                return@fromCallable NotesListOperationResult.Completed(items)

            } catch (ex: Exception) {
                return@fromCallable NotesListOperationResult.Failed(ex.toString())
            }
        }.subscribeOn(Schedulers.io())
    }
}