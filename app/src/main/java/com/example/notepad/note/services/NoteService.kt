package com.example.notepad.note.services

import android.content.Context
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteOperationResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class NoteService(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }

    fun changeFavouriteStatus(note: Note): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            try {
                note.isFavourite = !note.isFavourite
                NoteOperationResult.Completed
            } catch (ex: Exception) {
                NoteOperationResult.Failed(ex.toString())
            }
        }
    }

    fun validateNote(title: String): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            try {
                if (title.isEmpty())
                    return@fromCallable NoteOperationResult.Failed("Title must not be blank!")
                if (title.first().isUpperCase())
                    return@fromCallable NoteOperationResult.Completed

                NoteOperationResult.Failed("Title must begin with upper case letter!")
            } catch (ex: Exception) {
                NoteOperationResult.Failed(ex.toString())
            }
        }
    }

    fun saveNote(note: Note): Observable<NoteOperationResult> {
        return Observable.fromCallable {
            try {
                if (note.title?.isNotEmpty() != true)
                    return@fromCallable NoteOperationResult.Failed("Title cannot be blank!")
                if (note.title?.first()?.isUpperCase() != true)
                    return@fromCallable NoteOperationResult.Failed("Title must start with upper case letter!")

                db.insert(note)
                NoteOperationResult.Completed
            } catch (ex: Exception) {
                NoteOperationResult.Failed(ex.toString())
            }
        }.subscribeOn(Schedulers.io())
    }
}