package com.example.notepad.utils.customMatchers

import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun childAtPositionOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("\nwith child at position: $childPosition at parent view of type $parentMatcher")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) {
                return parentMatcher.matches(view.parent)
            }
            val group = view.parent as ViewGroup
            return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
        }
    }
}