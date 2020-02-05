package com.example.notepad.db

import android.content.Context
import com.example.notepad.db.models.Note
import com.google.gson.reflect.TypeToken

class Repository<T>(context: Context) : DataBase(context) {

    fun saveItemsList(key: String, items: ArrayList<T>) {
        val editor = mPrefs.edit()
        val json: String = mGson.toJson(items)
        editor.putString(key, json)
        editor.apply()
    }

    fun getItemsList(key: String): ArrayList<T> {
        val emptyList: String = mGson.toJson(ArrayList<Note>())

        return mGson.fromJson(
            mPrefs.getString(key, emptyList),
            object : TypeToken<ArrayList<Note>>() {}.type
        )
    }

    fun updateItem(item: T) {
        val allItems = getItemsList(allNotes)
        allItems[allItems.indexOf(item)] = item

        saveItemsList(allNotes, allItems)
    }

//    fun getItemsList(key: String): ArrayList<T> {
//        val emptyList: String = mGson.toJson(ArrayList<T>())
//
//        return mGson.fromJson(
//            mPrefs.getString(key, emptyList),
//            object : TypeToken<ArrayList<T>>() {}.type
//        )
//    }

    companion object {
        const val allNotes = "AllNotes"
    }
}