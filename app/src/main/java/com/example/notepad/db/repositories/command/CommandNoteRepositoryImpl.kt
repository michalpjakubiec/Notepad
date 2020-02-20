package com.example.notepad.db.repositories.command

import com.example.notepad.db.models.Note
import com.example.notepad.db.source.local.NoteDao
import io.reactivex.Completable

class CommandNoteRepositoryImpl(
    private val db: NoteDao
) : CommandNoteRepository {

    override fun insertOrReplace(note: Note): Completable {
        return db.insertOrReplace(note)
    }

    override fun insertOrReplace(notes: List<Note>): Completable {
        return db.insertOrReplace(notes)
    }

    override fun delete(note: Note): Completable {
        return db.delete(note)
    }

    override fun update(note: Note): Completable {
        return db.update(note)
    }
}