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
import java.lang.Exception

class NoteService(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }
    private val api by lazy { NoteAPIService.getService() }
    private val noteType = object : TypeToken<Note>() {}.type

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
        return Observable.fromCallable {
            val note = db.getNoteById(id) ?: throw Exception("Note not found")
            NoteOperationResult.Completed(note) as NoteOperationResult
        }
            .onErrorResumeNext(
                api.get(id).map {
                    val note = Gson().fromJson(it, noteType) as Note
                    NoteOperationResult.Completed(note) as NoteOperationResult
                })
            .onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return api.update(note.id, note).map {
            Gson().fromJson(it, noteType) as Note
        }
            .doOnNext { db.insert(it) }
            .map { NoteOperationResult.Completed(it) as NoteOperationResult }
            .onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }

    fun saveNote(note: Note): Observable<NoteOperationResult> {
        return api.add(note).map {
            Gson().fromJson(it, noteType) as Note
        }
            .doOnNext { db.insert(it) }
            .map {
                NoteOperationResult.Completed(note) as NoteOperationResult
            }
            .onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }
}