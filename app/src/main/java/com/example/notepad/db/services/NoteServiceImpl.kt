package com.example.notepad.db.services

//
//import android.content.Context
//import com.example.notepad.db.models.Note
//import com.example.notepad.db.services.responses.DeleteNoteIOResponse
//import com.example.notepad.db.services.responses.NoteIOResponse
//import com.example.notepad.db.services.responses.NotesListIOResponse
//import com.google.gson.Gson
//import io.reactivex.Observable
//import org.kodein.di.Kodein
//import org.kodein.di.KodeinAware
//import org.kodein.di.android.kodein
//
//class NoteServiceImpl(context: Context) : KodeinAware {
//    override val kodein: Kodein by kodein(context)
//
//        fun loadNotes(filter: String, limit: Int, skip: Int): Observable<NotesListDbResponse> {
//            return Observable.fromCallable {
//                try {
//                    if (filter.isNotEmpty() && filter.length < 3)
//                        return@fromCallable NotesListDbResponse(
//                            "Query must be longer than 2 characters",
//                            listOf<Note>()
//                        )
//
//                    val items: List<Note> =
//                        db.allNotesFilterByTitleOrderByDateLimitSkip(filter, limit, skip)
//
//                    if (items.size < limit)
//                        throw Error("Page is not full")
//
//                    NotesListDbResponse("", items)
//
//                } catch (ex: Exception) {
//                    NotesListDbResponse(ex.toString(), listOf<Note>())
//                }
//            }.onErrorResumeNext(api.getNotes(limit, skip, filter).map { json ->
//                val items: List<Note> = Gson().fromJson(json, listType)
//                db.insert(items)
//                NotesListDbResponse("", items)
//            })
//                .onErrorReturn { NotesListDbResponse(it.message.toString(), listOf()) }
//                .subscribeOn(Schedulers.io())
//        }
//
//        fun loadNotes(limit: Int, skip: Int): Observable<NotesListDbResponse> {
//            return Observable.fromCallable {
//                try {
//                    val items = db.allNotesOrderByDateLimitSkip(limit, skip)
//                    if (items.size < limit)
//                        throw Error("Page is not full")
//
//                    return@fromCallable NotesListDbResponse("", items)
//
//                } catch (ex: Exception) {
//                    return@fromCallable NotesListDbResponse(ex.toString(), listOf())
//                }
//            }.onErrorResumeNext(
//                api.getNotes(limit, skip).map { json ->
//                    val items: List<Note> = Gson().fromJson(json, listType)
//                    db.insert(items)
//                    NotesListDbResponse("", items)
//                })
//                .onErrorReturn { NotesListDbResponse(it.message.toString(), listOf()) }
//                .subscribeOn(Schedulers.io())
//        }
//    }