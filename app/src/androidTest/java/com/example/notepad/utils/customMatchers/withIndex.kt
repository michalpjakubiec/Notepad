package com.example.notepad.utils.customMatchers

import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?>? {
    return object : TypeSafeMatcher<View>() {
        var currentIndex = 0
        var viewObjHash = 0

        override fun describeTo(description: Description) {
            description.appendText("with index: $index of view: $matcher")
        }

        override fun matchesSafely(view: View): Boolean {
            if (matcher.matches(view) && currentIndex++ == index) {
                viewObjHash = view.hashCode()
            }
            return view.hashCode() == viewObjHash
        }
    }
}