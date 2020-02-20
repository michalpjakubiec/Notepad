package com.example.notepad.db.source.remote

import com.example.notepad.db.models.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NoteApi {
    private val listType = object : TypeToken<List<Note>>() {}.type
    private val noteType = object : TypeToken<Note>() {}.type

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://kotlinappapi.azurewebsites.net/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create<NoteAPIService>(
        NoteAPIService::class.java
    )

    fun getById(id: Int): Observable<Note> {
        return api.get(id).map {
            Gson().fromJson(it, noteType) as Note
        }.timeout(5, TimeUnit.SECONDS)
    }

    fun delete(id: Int): Observable<Int> {
        return api.delete(id).map {
            (Gson().fromJson(it, noteType) as Note).id
        }.timeout(5, TimeUnit.SECONDS)
    }

    fun save(note: Note): Observable<Note> {
        return api.add(note).map {
            Gson().fromJson(it, noteType) as Note
        }.timeout(5, TimeUnit.SECONDS)
    }

    fun update(id: Int, note: Note): Observable<Note> {
        return api.update(id, note).map {
            Gson().fromJson(it, noteType) as Note
        }.timeout(5, TimeUnit.SECONDS)
    }

    fun getNotes(take: Int, skip: Int, query: String): Observable<List<Note>> {
        return api.getNotes(take, skip, query).map {
            Gson().fromJson(it, listType) as List<Note>
        }.timeout(5, TimeUnit.SECONDS)
    }

    fun getNotes(take: Int, skip: Int): Observable<List<Note>> {
        return api.getNotes(take, skip).map {
            Gson().fromJson(it, listType) as List<Note>
        }.timeout(5, TimeUnit.SECONDS)
    }

    fun getNotes(take: Int): Observable<List<Note>> {
        return api.getNotes(take).map {
            Gson().fromJson(it, listType) as List<Note>
        }.timeout(5, TimeUnit.SECONDS)
    }
}

