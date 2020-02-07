package com.example.notepad.notesList.services

import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.models.Note
import com.example.notepad.notesList.utils.NotesListNextPageResult
import io.reactivex.Observable
import java.lang.Exception

class NotesPageLoader {
    fun loadPage(
        pageSearchPair: Pair<Int, String>,
        db: NoteDatabase
    ): Observable<NotesListNextPageResult> {
        try {
            if (pageSearchPair.first < 0)
                return Observable.just(NotesListNextPageResult.Error("Unable to load items from page: ${pageSearchPair.first}"))

            val items: List<Note> =
                if (pageSearchPair.second.isEmpty())
                    db.noteDao().allNotesOrderByDateLimitSkip(10, pageSearchPair.first * 10)
                else
                    db.noteDao().allNotesFilterByTitleOrderByDateLimitSkip(
                        pageSearchPair.second,
                        10,
                        pageSearchPair.first * 10
                    )

            return Observable.just(NotesListNextPageResult.Completed(ArrayList(items)))
        } catch (ex: Exception) {
            return Observable.just(NotesListNextPageResult.Error(ex.toString()))
        }
    }
}