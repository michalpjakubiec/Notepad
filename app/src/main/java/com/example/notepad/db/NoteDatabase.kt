package com.example.notepad.db

import android.content.Context
import androidx.room.*
import com.example.notepad.db.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null
        @Synchronized
        fun get(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "NoteDatabase"
                ).build()
            }
            return instance!!
        }
    }
}