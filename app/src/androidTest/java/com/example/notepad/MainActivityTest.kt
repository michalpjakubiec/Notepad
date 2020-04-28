package com.example.notepad

import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.main.ui.MainActivity
import com.example.notepad.utils.*
import com.example.notepad.utils.customActions.waitForViewFor
import com.example.notepad.utils.customMatchers.DrawableMatchers.withImageButtonDrawable
import com.example.notepad.utils.customMatchers.withIndex
import com.example.notepad.utils.customRules.ScreenshotTestRule
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers.*
import org.jetbrains.anko.recyclerview.v7._RecyclerView
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)

class MainActivityTest {
    @get:Rule val mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    @get:Rule val screenshotTestRule = ScreenshotTestRule()
    @get:Rule val globalTimeout = Timeout.millis(testCaseTimeout)!!

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
        }
    }

    @Before
    fun before() {
        onView(isRoot())
            .perform(
                waitForViewFor(
                    isAssignableFrom(NoteViewHolderUI::class.java),
                    waitForViewTimeout
                )
            )
    }


    @Test
    fun shouldClickOnSearchbarAndInputText() {
        onView((isAssignableFrom(TextInputEditText::class.java)))
            .check(matches(isDisplayed()))
            .perform(
                click(),
                typeText(titleSearchbox)
            )

        onView(
            allOf(
                isDescendantOfA(isAssignableFrom(RecyclerView::class.java)),
                isAssignableFrom(NoteViewHolderUI::class.java)
            )
        )

        // TODO: search assertion
    }

    @Test
    fun shouldOpenEditNoteViewOnNoteLongClick() {
        onView(instanceOf(_RecyclerView::class.java))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    allOf(
                        isAssignableFrom(NoteViewHolderUI::class.java),
                        hasDescendant(withText(titleEditNote))
                    ), longClick()
                )
            )

        onView(withHint(R.string.titleHint))
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText(titleEditNote)
                    )
                )
            )

        onView(withHint(R.string.contentHint))
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText(contentEditNote)
                    )
                )
            )
    }

    @Test
    fun shouldArchiveNote() {
        onView(
            allOf(
                isDescendantOfA(isAssignableFrom(NoteViewHolderUI::class.java)),
                hasSibling(withText(titleToArchive)),
                withIndex(isAssignableFrom(ImageButton::class.java), 1)
            )
        ).check(matches(withImageButtonDrawable(R.drawable.ic_archive_white_24dp)))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(isAssignableFrom(NoteViewHolderUI::class.java)),
                hasSibling(withText(titleToArchive)),
                withIndex(isAssignableFrom(ImageButton::class.java), 1)
            )
        ).check(matches(withImageButtonDrawable(R.drawable.ic_unarchive_white_24dp)))
    }

    @Test
    fun shouldDeleteNote() {
        val noteToDelete = onView(
            allOf(
                isAssignableFrom(NoteViewHolderUI::class.java),
                withChild(withText(titleDelete))
            )
        )

        noteToDelete
            .check(matches(isDisplayed()))

        noteToDelete
            .perform(swipeRight())
            .check(matches(not(isDisplayed())))
    }
}



