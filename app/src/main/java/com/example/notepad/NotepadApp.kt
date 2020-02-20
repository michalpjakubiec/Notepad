package com.example.notepad

import android.app.Application
import com.example.notepad.db.repositories.command.CommandNoteRepository
import com.example.notepad.db.repositories.command.CommandNoteRepositoryImpl
import com.example.notepad.db.repositories.query.QueryNoteRepository
import com.example.notepad.db.repositories.query.QueryNoteRepositoryImpl
import com.example.notepad.db.services.command.CommandNoteService
import com.example.notepad.db.services.command.CommandNoteServiceImpl
import com.example.notepad.db.services.query.QueryNoteService
import com.example.notepad.db.services.query.QueryNoteServiceImpl
import com.example.notepad.db.source.local.NoteDao
import com.example.notepad.db.source.local.NoteDatabase
import com.example.notepad.db.source.remote.NoteApi
import com.example.notepad.note.helpers.NoteHelper
import com.example.notepad.note.helpers.NoteUseCase
import com.example.notepad.note.mvi.NotePresenter
import com.example.notepad.note.mvi.NoteReducer
import com.example.notepad.notesList.helpers.NotesListHelper
import com.example.notepad.notesList.helpers.NotesListUseCase
import com.example.notepad.notesList.mvi.NotesListPresenter
import com.example.notepad.notesList.mvi.NotesListReducer
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class NotepadApp : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NotepadApp))

        this.bind<NoteApi>() with singleton { NoteApi() }
        this.bind<NoteDao>() with singleton { NoteDatabase.get(instance()).noteDao() }

        this.bind<QueryNoteRepository>() with singleton { QueryNoteRepositoryImpl(instance()) }
        this.bind<CommandNoteRepository>() with singleton { CommandNoteRepositoryImpl(instance()) }

        this.bind<QueryNoteService>() with singleton {
            QueryNoteServiceImpl(
                instance(),
                instance()
            )
        }
        this.bind<CommandNoteService>() with singleton {
            CommandNoteServiceImpl(
                instance(),
                instance()
            )
        }

        this.bind<NotesListHelper>() with singleton { NotesListHelper(instance(), instance()) }
        this.bind<NoteHelper>() with singleton { NoteHelper(instance(), instance()) }

        this.bind<NotesListUseCase>() with singleton { NotesListUseCase(instance()) }
        this.bind<NoteUseCase>() with singleton { NoteUseCase(instance()) }

        this.bind<NotesListReducer>() with singleton { NotesListReducer() }
        this.bind<NoteReducer>() with singleton { NoteReducer() }

        this.bind<NotesListPresenter>() with singleton {
            NotesListPresenter(
                instance(),
                instance()
            )
        }
        this.bind<NotePresenter>() with singleton { NotePresenter(instance(), instance()) }
    }
}