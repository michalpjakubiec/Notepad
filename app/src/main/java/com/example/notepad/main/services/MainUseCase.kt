package com.example.notepad.main.services

import android.content.Context
import com.example.notepad.main.utils.MainRedirectionResult
import com.example.notepad.main.utils.ReplaceFragmentArguments
import io.reactivex.Observable

class MainUseCase(context: Context) {
    private val service by lazy { MainService(context) }

    fun getFragment(arguments: ReplaceFragmentArguments): Observable<MainRedirectionResult> {
        if (arguments.redirectToNoteFragment)
            return service.getNoteFragment(arguments)
        else if (arguments.redirectToNotesListFragment)
            return service.getNotesListFragment()

        return Observable.just(MainRedirectionResult.Failed("Faulty redirection parameters!"))
    }
}