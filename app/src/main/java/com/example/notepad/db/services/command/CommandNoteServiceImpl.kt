package com.example.notepad.db.services.command

import com.example.notepad.db.models.Note
import com.example.notepad.db.repositories.command.CommandNoteRepository
import com.example.notepad.db.services.responses.DeleteNoteIOResponse
import com.example.notepad.db.services.responses.NoteIOResponse
import com.example.notepad.db.source.remote.NoteApi
import io.reactivex.Observable

class CommandNoteServiceImpl(
    private val repository: CommandNoteRepository,
    private val api: NoteApi
) : CommandNoteService {

    override fun saveNote(note: Note): Observable<NoteIOResponse> {
        return api.save(note)
            .doOnNext {
                repository.insertOrReplace(it)
            }
            .map {
                NoteIOResponse(
                    "",
                    note
                )
            }
            .doOnError {
                NoteIOResponse(
                    it.message.toString()
                )
            }
    }

    override fun deleteNote(
        id: Int,
        note: Note
    ): Observable<DeleteNoteIOResponse> {
        return api.delete(id)
            .doOnNext {
                repository.delete(note)
            }
            .map {
                DeleteNoteIOResponse(
                    "",
                    it
                )
            }
            .onErrorReturn {
                DeleteNoteIOResponse(
                    it.message.toString()
                )
            }
    }

    override fun updateNote(note: Note): Observable<NoteIOResponse> {
        return api.update(note.id, note)
            .doOnNext {
                repository.update(it)
            }
            .map {
                NoteIOResponse("", it)
            }
            .onErrorReturn {
                NoteIOResponse(it.message.toString())
            }
    }
}