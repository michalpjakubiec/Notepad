package com.example.notepad.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.notepad.base.ReplaceFragment
import com.example.notepad.notesList.ui.NotesListFragment
import org.jetbrains.anko.linearLayout

class MainActivity : AppCompatActivity(), ReplaceFragment {
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = NotesListFragment()
        val containerId = View.generateViewId()
        container = linearLayout {
            id = containerId
        }

        replaceFragment(fragment)
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(container.id, fragment).addToBackStack(null).commit()
    }

}
