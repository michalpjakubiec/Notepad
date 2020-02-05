package com.example.notepad.db

import android.content.Context
import com.example.notepad.db.models.Note
import com.google.gson.reflect.TypeToken

class NoteRepository(context: Context) : DataBase(context) {
    fun save(key: String, notes: ArrayList<Note>) {
        val editor = mPrefs.edit()
        val json: String = mGson.toJson(notes)
        editor.putString(key, json)
        editor.apply()
    }

    fun getAll(key: String): ArrayList<Note> {
        val json = mGson.toJson(ArrayList<Note>())

        return mGson.fromJson(
            mPrefs.getString(key, json),
            object : TypeToken<ArrayList<Note>>() {}.type
        )
    }

    fun update(note: Note) {
        val allNotes = getAll(notesTableKey)
        val noteToReplace = allNotes.firstOrNull { x -> x.id == note.id }
        val index = allNotes.indexOf(noteToReplace)

        allNotes[index] = note
        save(notesTableKey, allNotes)
    }

    companion object {
        private var INSTANCE: NoteRepository? = null
        fun getInstance(context: Context): NoteRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = NoteRepository(context)
                }
            }
            return INSTANCE as NoteRepository
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        const val notesTableKey = "AllNotes"
    }
}