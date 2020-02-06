package com.example.notepad.db

import androidx.room.*
import com.example.notepad.db.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY created DESC LIMIT :limit OFFSET :offset")
    fun allNotesOrderByDateLimit(limit: Int, offset: Int): List<Note>

    @Query("SELECT * FROM Note")
    fun allNotes(): List<Note>

    @Query("SELECT * FROM Note LIMIT :limit")
    fun allNotesLimit(limit: Int): List<Note>

    @Query("SELECT * FROM Note WHERE title Like :query")
    fun allNotesFilterByTitle(query: String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(notes: List<Note>)

    @Update
    fun update(note: Note)
}