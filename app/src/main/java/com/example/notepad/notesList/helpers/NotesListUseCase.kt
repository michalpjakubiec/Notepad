package com.example.notepad.notesList.helpers

import android.content.Context
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NoteOperationResult
import com.example.notepad.notesList.utils.NotesListOperationResult
import io.reactivex.Observable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NotesListUseCase(context: Context) : KodeinAware {
    override val kodein: Kodein by kodein(context)
    private val helper: NotesListHelper by instance()

    fun loadNextPage(filter: String = "", skip: Int = 0): Observable<NotesListOperationResult> {
        return helper.loadNotes(filter, 10, skip)
    }

    fun deleteNote(note: Note): Observable<NoteOperationResult> {
        return helper.deleteNote(note.id, note)
    }

    fun showNote(id: Int): Observable<NoteOperationResult> {
        return Observable.just(NoteOperationResult.Completed(id))
    }

    fun updateNote(note: Note): Observable<NoteOperationResult> {
        return helper.updateNote(note)
    }
}