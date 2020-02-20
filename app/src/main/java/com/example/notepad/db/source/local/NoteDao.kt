package com.example.notepad.db.source.local

import androidx.room.*
import com.example.notepad.db.models.Note
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id: Int): Maybe<Note>

    @Query("SELECT * FROM Note ORDER BY created DESC LIMIT :limit OFFSET :offset")
    fun allNotesOrderByDateLimitSkip(limit: Int, offset: Int): Observable<List<Note>>

    @Query("SELECT * FROM Note")
    fun allNotes(): Observable<List<Note>>

    @Query("SELECT * FROM Note LIMIT :limit")
    fun allNotesLimit(limit: Int): Observable<List<Note>>

    @Query("SELECT * FROM Note WHERE title LIKE :query ORDER BY created DESC LIMIT :limit OFFSET :offset")
    fun allNotesFilterByTitleOrderByDateLimitSkip(
        query: String,
        limit: Int,
        offset: Int
    ): Observable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(notes: List<Note>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(note: Note): Completable

    @Delete
    fun delete(note: Note): Completable

    @Update
    fun update(note: Note): Completable
}