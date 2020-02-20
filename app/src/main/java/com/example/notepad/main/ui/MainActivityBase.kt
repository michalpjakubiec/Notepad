package com.example.notepad.main.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.notepad.base.HaveTag
import com.example.notepad.base.ReplaceFragment
import com.example.notepad.components.main.MainUI
import com.example.notepad.main.mvi.MainPresenter
import com.example.notepad.main.mvi.MainView
import com.example.notepad.main.utils.ReplaceFragmentArguments
import com.example.notepad.notesList.ui.NotesListFragment
import com.example.notepad.utils.MAIN_ACTIVITY_CONTAINER_ID
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

abstract class MainActivityBase : MviActivity<MainView, MainPresenter>(),
    MainView,
    ReplaceFragment, KodeinAware {

    override val kodein: Kodein by closestKodein()
    lateinit var ui: MainUI
    val redirectSubject: PublishSubject<ReplaceFragmentArguments> = PublishSubject.create()
    lateinit var mainContainer: LinearLayout
    override var replaceIntent: Observable<ReplaceFragmentArguments> = redirectSubject
    override var currentFragmentTag: String? = null

    override fun createPresenter(): MainPresenter = MainPresenter(this)

    override fun replaceFragment(containerId: Int, fragment: Fragment) {
        val fragmentTag = (fragment as HaveTag).getFragmentTag()
        val transaction =
            supportFragmentManager.beginTransaction().replace(containerId, fragment, fragmentTag)
        if (currentFragmentTag != null)
            transaction.addToBackStack(currentFragmentTag)
        transaction.commit()

        currentFragmentTag = fragmentTag
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainContainer = linearLayout {
            lparams(matchParent, matchParent)
            id = MAIN_ACTIVITY_CONTAINER_ID
        }
        if (savedInstanceState == null)
            replaceFragment(mainContainer.id, NotesListFragment())
    }
}