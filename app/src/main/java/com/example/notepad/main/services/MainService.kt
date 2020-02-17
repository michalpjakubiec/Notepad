package com.example.notepad.main.services

import android.content.Context
import android.os.Bundle
import com.example.notepad.main.ui.MainActivity
import com.example.notepad.main.utils.MainRedirectionResult
import com.example.notepad.main.utils.ReplaceFragmentArguments
import com.example.notepad.note.ui.NoteFragment
import com.example.notepad.notesList.ui.NotesListFragment
import com.example.notepad.utils.NOTE_FRAGMENT_TAG
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainService(context: Context) {
    private val fragmentManager by lazy { (context as MainActivity).supportFragmentManager }

    fun getNoteFragment(arguments: ReplaceFragmentArguments): Observable<MainRedirectionResult> {
        return Observable.fromCallable {
            val fragment = fragmentManager.findFragmentByTag(NOTE_FRAGMENT_TAG) as? NoteFragment
                ?: NoteFragment()

            val args = Bundle()
            args.putInt("ID", arguments.noteId)
            fragment.arguments = args

            MainRedirectionResult.Completed(fragment) as MainRedirectionResult
        }.subscribeOn(Schedulers.io())
            .onErrorReturn { MainRedirectionResult.Failed(it.message.toString()) }
    }

    fun getNotesListFragment(): Observable<MainRedirectionResult> {
        return Observable.fromCallable {
            val fragment =
                fragmentManager.findFragmentByTag(NOTE_FRAGMENT_TAG) as? NotesListFragment
                    ?: NotesListFragment()

            MainRedirectionResult.Completed(fragment) as MainRedirectionResult
        }.subscribeOn(Schedulers.io())
            .onErrorReturn { MainRedirectionResult.Failed(it.message.toString()) }
    }
}