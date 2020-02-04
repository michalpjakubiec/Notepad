package com.example.notepad.db

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

abstract class DataBase(context: Context) {
    val mPrefs: SharedPreferences = context.getSharedPreferences("DATABASE", Context.MODE_PRIVATE)
    val mGson = Gson()
}