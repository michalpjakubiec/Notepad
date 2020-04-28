package com.example.notepad.utils.customMatchers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


object DrawableMatchers {
    fun withTextViewDrawable(resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Drawable with id: $resourceId at ActionMenuItemView")
            }

            override fun matchesSafely(textView: TextView): Boolean {
                for (drawable in textView.compoundDrawables) {
                    if (isSameBitmap(textView.context, drawable, resourceId)) {
                        return true
                    }
                }
                return false
            }
        }
    }

    fun withImageButtonDrawable(resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View, ImageButton>(ImageButton::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Drawable with id: $resourceId at ImageButton")
            }

            override fun matchesSafely(imageButton: ImageButton): Boolean {
                return isSameBitmap(
                    imageButton.context,
                    imageButton.drawable,
                    resourceId)
            }
        }
    }

    private fun isSameBitmap(context: Context, drawable: Drawable?, resourceId: Int): Boolean {
        var thisDrawable = drawable
        var otherDrawable = ContextCompat.getDrawable(context, resourceId)

        if (thisDrawable == null || otherDrawable == null) {
            return false
        }
        if (thisDrawable is StateListDrawable && otherDrawable is StateListDrawable) {
            thisDrawable = thisDrawable.current
            otherDrawable = otherDrawable.current
        }

        val bitmap = if (thisDrawable is VectorDrawable) vectorToBitmap(thisDrawable)
        else (thisDrawable as BitmapDrawable).bitmap
        val otherBitmap = if (otherDrawable is VectorDrawable) vectorToBitmap(otherDrawable)
        else (otherDrawable as BitmapDrawable).bitmap
        return bitmap.sameAs(otherBitmap)
    }

    private fun vectorToBitmap(vectorDrawable: VectorDrawable): Bitmap {
        return vectorDrawable.toBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888)
    }
}