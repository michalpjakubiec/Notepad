package com.example.notepad.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notepad.db.models.Note
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// dodaj retrofit api
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
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            fillInDb(context.applicationContext)
                        }
                    }).build()
            }
            return instance!!
        }

        private fun fillInDb(context: Context) {
            ioThread {
                val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
                val notes = ArrayList<Triple<Date, String, String>>()

                for (i in 1..500) {
                    notes.add(Triple(
                        Date(), "Title $i",
                        (1..150).map { Random.nextInt(0, charPool.size) }
                            .map(charPool::get)
                            .joinToString("")))
                }

                get(context).noteDao().insert(
                    notes.map {
                        Note(
                            id = 0,
                            created = it.first.time,
                            title = it.second,
                            content = it.third,
                            isArchival = false
                        )
                    })
            }
        }
    }
}