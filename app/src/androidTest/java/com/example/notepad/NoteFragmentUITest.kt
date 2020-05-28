package com.example.notepad

import android.view.ViewGroup
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.notepad.components.notesList.NoteViewHolderUI
import com.example.notepad.main.ui.MainActivity
import com.example.notepad.utils.*
import com.example.notepad.utils.customActions.waitForViewUntil
import com.example.notepad.utils.customMatchers.DrawableMatchers.withImageButtonDrawable
import com.example.notepad.utils.customMatchers.DrawableMatchers.withTextViewDrawable
import com.example.notepad.utils.customMatchers.withIndex
import com.example.notepad.utils.customRules.ScreenshotTestRule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)

class NoteFragmentUITest {
    @get:Rule val mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    @get:Rule val screenshotTestRule = ScreenshotTestRule()
    @get:Rule val globalTimeout = Timeout.seconds(testCaseTimeoutSeconds)!!

    @Before
    fun before() {
        onView(isAssignableFrom(FloatingActionButton::class.java))
            .perform(click())
    }


    @Test
    fun shouldFavouriteIconBeDisabledOnViewOpen() {
        onView(withContentDescription(R.string.toolBarSaveTitle))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun shouldValidateTitleWhileTyping() {
        val saveIcon = onView(withContentDescription(R.string.toolBarSaveTitle))

        onView(
            withIndex(
                allOf(
                    isDescendantOfA(isAssignableFrom(ViewGroup::class.java)),
                    isAssignableFrom(TextView::class.java),
                    isDisplayed()
                ), 0
            )
        ).perform(typeText(titleInvalid), closeSoftKeyboard())
        saveIcon.check(matches(not(isEnabled())))

        onView(withHint(R.string.titleHint))
            .perform(clearText())
        saveIcon.check(matches(not(isEnabled())))

        onView(withHint(R.string.titleHint))
            .perform(
                clearText(),
                typeText(titleValid),
                closeSoftKeyboard()
            )
        saveIcon.check(matches(isEnabled()))
    }

    @Test
    fun shouldAddNewNoteAndDisplayItOnNotesList() {
        onView(withHint(R.string.titleHint))
            .perform(typeText(titleNewNote), closeSoftKeyboard())

        onView(withHint(R.string.contentHint))
            .perform(typeText(contentText), closeSoftKeyboard())

        onView(withContentDescription(R.string.toolBarSaveTitle))
            .perform(click())

        onView(withText(R.string.notesSavedToast))
            .inRoot(withDecorView(not((mActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))

        onView(isRoot())
            .perform(
                waitForViewUntil(
                    isAssignableFrom(NoteViewHolderUI::class.java),
                    waitForViewTimeout
                )
            )

//        alternate wait
//        mDevice.wait(
//            Until.findObject(By.clazz(_RecyclerView::class.java)), findViewTimeout)

        onView(
            allOf(
                isDescendantOfA(isAssignableFrom(NoteViewHolderUI::class.java)),
                withText(titleNewNote)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldClickFavouriteIconAndMakeNoteFavourite() {
        onView(withHint(R.string.titleHint))
            .perform(typeText(titleFavNote), closeSoftKeyboard())

        onView(withContentDescription(R.string.toolBarFavouriteTitle))
            .perform(click())
            .check(matches(withTextViewDrawable(R.drawable.ic_favorite_white_24dp)))

        onView(withContentDescription(R.string.toolBarSaveTitle))
            .perform(click())

        onView(isRoot())
            .perform(
                waitForViewUntil(
                    isAssignableFrom(NoteViewHolderUI::class.java),
                    waitForViewTimeout
                )
            )

        onView(
            allOf(
                isDescendantOfA(isAssignableFrom(NoteViewHolderUI::class.java)),
                hasSibling(withText(titleFavNote)),
                withImageButtonDrawable(R.drawable.ic_favorite_white_24dp)
            )
        ).check(matches(isDisplayed()))
    }
}