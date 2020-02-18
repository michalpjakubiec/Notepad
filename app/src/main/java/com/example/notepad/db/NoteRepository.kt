package com.example.notepad.db

import android.content.Context
import com.example.notepad.db.models.Note
import com.example.notepad.db.response.DeleteNoteDbResponse
import com.example.notepad.db.response.NoteDbResponse
import com.example.notepad.db.response.NotesListDbResponse
import com.example.notepad.service.NoteAPIService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NoteRepository(context: Context) {
    private val db by lazy { NoteDatabase.get(context).noteDao() }
    private val api by lazy { NoteAPIService.getService() }
    private val listType = object : TypeToken<List<Note>>() {}.type
    private val noteType = object : TypeToken<Note>() {}.type


    fun saveNote(note: Note): Observable<NoteDbResponse> {
        return api.add(note).map {
            Gson().fromJson(it, noteType) as Note
        }
            .doOnNext { db.insert(it) }
            .map {
                NoteDbResponse("", note)
            }
            .onErrorReturn { NoteDbResponse(it.message.toString(), null) }
            .subscribeOn(Schedulers.io())
    }

    fun deleteNote(
        id: Int,
        note: Note
    ): Observable<DeleteNoteDbResponse> {
        return api.delete(id).map {
            id
        }.doOnNext { db.delete(note) }
            .map { DeleteNoteDbResponse("", it) }
            .onErrorReturn { DeleteNoteDbResponse(it.message.toString(), -1) }
            .subscribeOn(Schedulers.io())
    }

    fun getNote(id: Int): Observable<NoteDbResponse> {
        return Observable.fromCallable {
            val note = db.getNoteById(id) ?: throw Exception("Note not found")
            NoteDbResponse("", note)
        }.onErrorResumeNext(
            api.get(id).map {
                val note = Gson().fromJson(it, noteType) as Note?
                NoteDbResponse("", note)
            }).onErrorReturn { NoteDbResponse(it.message.toString(), null) }
            .subscribeOn(Schedulers.io())
    }

    fun updateNote(
        note: Note
    ): Observable<NoteDbResponse> {
        return api.update(note.id, note).map { json ->
            Gson().fromJson(json, noteType) as Note
        }.doOnNext { updatedNote ->
            db.insert(updatedNote)
        }.map { update ->
            NoteDbResponse("", update)
        }
            .onErrorReturn { NoteDbResponse(it.message.toString(), null) }
            .subscribeOn(Schedulers.io())
    }

    fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListDbResponse> {
        return Observable.fromCallable {
            try {
                if (filter.isNotEmpty() && filter.length < 3)
                    return@fromCallable NotesListDbResponse(
                        "Query must be longer than 2 characters",
                        listOf<Note>()
                    )

                val items: List<Note> =
                    db.allNotesFilterByTitleOrderByDateLimitSkip(filter, limit, skip)

                if (items.size < limit)
                    throw Error("Page is not full")

                NotesListDbResponse("", items)

            } catch (ex: Exception) {
                NotesListDbResponse(ex.toString(), listOf<Note>())
            }
        }.onErrorResumeNext(api.getNotes(limit, skip, filter).map { json ->
            val items: List<Note> = Gson().fromJson(json, listType)
            db.insert(items)
            NotesListDbResponse("", items)
        })
            .onErrorReturn { NotesListDbResponse(it.message.toString(), listOf()) }
            .subscribeOn(Schedulers.io())
    }

    fun loadNotes(limit: Int, skip: Int): Observable<NotesListDbResponse> {
        return Observable.fromCallable {
            try {
                val items = db.allNotesOrderByDateLimitSkip(limit, skip)
                if (items.size < limit)
                    throw Error("Page is not full")

                return@fromCallable NotesListDbResponse("", items)

            } catch (ex: Exception) {
                return@fromCallable NotesListDbResponse(ex.toString(), listOf())
            }
        }.onErrorResumeNext(
            api.getNotes(limit, skip).map { json ->
                val items: List<Note> = Gson().fromJson(json, listType)
                db.insert(items)
                NotesListDbResponse("", items)
            })
            .onErrorReturn { NotesListDbResponse(it.message.toString(), listOf()) }
            .subscribeOn(Schedulers.io())
    }

    fun loadNotes(
        limit: Int,
        skip: Int,
        archivalFilter: Boolean,
        favouriteFilter: Boolean
    ): Observable<NotesListDbResponse> {
        return Observable.fromCallable {
            try {
                // do zastapienia api callem
                var items = db.allNotes()

                items = if (archivalFilter)
                    items.filter { it.isArchival }
                else
                    items.filter { it.isArchival }

                items = if (favouriteFilter)
                    items.filter { it.isFavourite }.drop(skip).take(limit)
                else
                    items.filter { !it.isFavourite }.drop(skip).take(limit)

                if (items.size < limit)
                    throw Error("Page is not full")

                return@fromCallable NotesListDbResponse("", items)

            } catch (ex: Exception) {
                return@fromCallable NotesListDbResponse(ex.toString(), listOf())
            }
        }.onErrorResumeNext(
            api.getNotes(limit * 10).map { json ->
                var items: List<Note> = Gson().fromJson(json, listType)

                items = if (archivalFilter)
                    items.filter { it.isArchival }
                else
                    items.filter { it.isArchival }

                items = if (favouriteFilter)
                    items.filter { it.isFavourite }.drop(skip).take(limit)
                else
                    items.filter { !it.isFavourite }.drop(skip).take(limit)

                db.insert(items)
                NotesListDbResponse("", items)
            })
            .onErrorReturn { NotesListDbResponse(it.message.toString(), listOf()) }
            .subscribeOn(Schedulers.io())
    }
}