package com.example.notepad.db.services.query

import com.example.notepad.db.repositories.query.QueryNoteRepository
import com.example.notepad.db.services.responses.NoteIOResponse
import com.example.notepad.db.services.responses.NotesListIOResponse
import com.example.notepad.db.source.remote.NoteApi
import io.reactivex.Observable

class QueryNoteServiceImpl(
    private val repository: QueryNoteRepository,
    private val api: NoteApi
) : QueryNoteService {

    override fun getNote(id: Int): Observable<NoteIOResponse> {
        return api.getById(id).map {
            NoteIOResponse("", it)
        }.doOnError {
            repository.getById(id).map {
                NoteIOResponse("", it)
            }
        }.onErrorReturn {
            NoteIOResponse(it.message.toString())
        }
    }

    override fun loadNotes(
        limit: Int, skip: Int, filter: String
    ): Observable<NotesListIOResponse> {
        return api.getNotes(limit, skip, filter).map {
            NotesListIOResponse("", it)
        }.doOnError {
            repository.getAll(filter, limit, skip).map {
                NotesListIOResponse("", it)
            }
        }.onErrorReturn {
            NotesListIOResponse(it.message.toString())
        }
    }
}