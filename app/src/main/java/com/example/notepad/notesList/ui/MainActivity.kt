package com.example.notepad.notesList.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = NotesListFragment()
        val containerId = View.generateViewId()
        linearLayout {
            id = containerId
            supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
        }
    }
}
