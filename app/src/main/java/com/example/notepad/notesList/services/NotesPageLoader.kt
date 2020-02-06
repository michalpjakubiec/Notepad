package com.example.notepad.notesList.services

import com.example.notepad.db.NoteDatabase
import com.example.notepad.notesList.utils.NotesListNextPageResult
import io.reactivex.Observable

class NotesPageLoader {
    fun loadPage(page: Int, db: NoteDatabase): Observable<NotesListNextPageResult> {
        if (page < 0)
            return Observable.just(NotesListNextPageResult.Error("Unable to load items from page: $page"))

        val items = db.noteDao().allNotesOrderByDateLimit(10, page * 10)
        return Observable.just(NotesListNextPageResult.Completed(ArrayList(items)))
    }
}