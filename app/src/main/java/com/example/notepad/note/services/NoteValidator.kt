package com.example.notepad.note.services

import com.example.notepad.note.utils.NoteValidationResult
import io.reactivex.Observable
import java.lang.Exception

class NoteValidator {

    fun validateNote(title: String): Observable<NoteValidationResult> {
        try {
            if (title.isEmpty())
                return Observable.just(NoteValidationResult.Failed("Title must not be blank!"))
            if (title.first().isUpperCase())
                return Observable.just(NoteValidationResult.Completed)

            return Observable.just(NoteValidationResult.Failed("Title must begin with upper case letter!"))
        } catch (ex: Exception) {
            return Observable.just(NoteValidationResult.Failed(ex.toString()))
        }
    }
}