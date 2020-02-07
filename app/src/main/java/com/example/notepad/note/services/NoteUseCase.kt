package com.example.notepad.note.services

import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.note.utils.NoteSaveResult
import com.example.notepad.note.utils.NoteValidationResult
import io.reactivex.Observable
import java.lang.Exception

class NoteUseCase {

    private val validator by lazy { NoteValidator() }

    fun validateNote(title: String): Observable<NoteValidationResult> {
        return validator.validateNote(title)
    }

    fun saveNote(note: Note, db: NoteDatabase): Observable<NoteSaveResult> {
        return try {
            if (note.title?.isNotEmpty() != true)
                return Observable.just(NoteSaveResult.Failed("Title cannot be blank!"))
            if (note.title?.first()?.isUpperCase() != true)
                return Observable.just(NoteSaveResult.Failed("Title must start with upper case letter!"))

            db.noteDao().insert(note)
            Observable.just(NoteSaveResult.Completed)
        } catch (ex: Exception) {
            Observable.just(NoteSaveResult.Failed(ex.toString()))
        }
    }
}