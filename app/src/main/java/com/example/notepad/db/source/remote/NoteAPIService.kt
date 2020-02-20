package com.example.notepad.db.source.remote

import com.example.notepad.db.models.Note
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*

interface NoteAPIService {
    @GET("api/notes")
    fun getNotes(
        @Query("take") take: Int
    ): Observable<JsonArray>

    @GET("api/notes")
    fun getNotes(
        @Query("take") take: Int,
        @Query("skip") skip: Int
    ): Observable<JsonArray>

    @GET("api/notes")
    fun getNotes(
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("caseInsensitiveSearchString") query: String
    ): Observable<JsonArray>

    @POST("api/notes")
    fun add(@Body note: Note): Observable<JsonObject>

    @DELETE("api/notes/{id}")
    fun delete(@Path("id") id: Int): Observable<JsonObject>

    @GET("api/notes/{id}")
    fun get(@Path("id") id: Int): Observable<JsonObject>

    @PUT("api/notes/{id}")
    fun update(@Path("id") id: Int, @Body note: Note): Observable<JsonObject>
}