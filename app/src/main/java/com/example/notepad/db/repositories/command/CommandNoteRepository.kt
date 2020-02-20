package com.example.notepad.db.repositories.command

import com.example.notepad.db.models.Note
import io.reactivex.Completable

interface CommandNoteRepository {
    fun insertOrReplace(note: Note): Completable

    fun insertOrReplace(notes: List<Note>): Completable

    fun delete(note: Note): Completable

    fun update(note: Note): Completable

}