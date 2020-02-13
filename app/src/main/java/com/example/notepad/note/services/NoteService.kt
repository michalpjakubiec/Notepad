package com.example.notepad.note.services

import android.content.Context
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteOperationResult
import com.example.notepad.service.NoteAPIService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Error
import java.lang.Exception

class NoteService(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }
    private val api by lazy { NoteAPIService.getService() }
    private val noteType = object : TypeToken<Note>() {}.type

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
        return api.add(note).map {
            Gson().fromJson(it, noteType) as Note
        }
            .doOnNext { db.insert(it) }
            .map {
                NoteOperationResult.Completed as NoteOperationResult
            }.subscribeOn(Schedulers.io())
            .onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
    }
}