package com.example.notepad.notesList.services

import android.content.Context
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import com.example.notepad.service.NoteAPIService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Error
import java.lang.Exception

class NotesListService(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }
    private val api by lazy { NoteAPIService.getService() }
    private val listType = object : TypeToken<List<Note>>() {}.type
    private val noteType = object : TypeToken<Note>() {}.type

    fun deleteNote(
        id: Int,
        note: Note
    ): Observable<NoteOperationResult> {
        return api.delete(id).map {
            id
        }.doOnNext { db.delete(note) }
            .map { NoteOperationResult.Completed(it) as NoteOperationResult }
            .onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }

    fun updateNote(
        note: Note
    ): Observable<NoteOperationResult> {
        return api.update(note.id, note).map { json ->
            Gson().fromJson(json, noteType) as Note
        }.doOnNext { updatedNote ->
            db.insert(updatedNote)
        }.map { update ->
            NoteOperationResult.Completed(update.id) as NoteOperationResult
        }
            .onErrorReturn { NoteOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }

    fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return Observable.fromCallable {
            try {
                if (filter.isNotEmpty() && filter.length < 3)
                    return@fromCallable NotesListOperationResult.Failed("Query must be longer than 2 characters")

                val items: List<Note> =
                    db.allNotesFilterByTitleOrderByDateLimitSkip(filter, limit, skip)

                if (items.size < limit)
                    throw Error("Page is not full")

                NotesListOperationResult.Completed(items)

            } catch (ex: Exception) {
                NotesListOperationResult.Failed(ex.toString())
            }
        }.onErrorResumeNext(api.getNotes(limit, skip, filter).map { json ->
            val items: List<Note> = Gson().fromJson(json, listType)
            db.insert(items)
            NotesListOperationResult.Completed(items)
        })
            .onErrorReturn { NotesListOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }

    fun loadNotes(limit: Int, skip: Int): Observable<NotesListOperationResult> {
        return Observable.fromCallable {
            try {
                val items = db.allNotesOrderByDateLimitSkip(limit, skip)
                if (items.size < limit)
                    throw Error("Page is not full")

                return@fromCallable NotesListOperationResult.Completed(items)

            } catch (ex: Exception) {
                return@fromCallable NotesListOperationResult.Failed(ex.toString())
            }
        }.onErrorResumeNext(
            api.getNotes(limit, skip).map { json ->
                val items: List<Note> = Gson().fromJson(json, listType)
                db.insert(items)
                NotesListOperationResult.Completed(items)
            })
            .onErrorReturn { NotesListOperationResult.Failed(it.message.toString()) }
            .subscribeOn(Schedulers.io())
    }
}