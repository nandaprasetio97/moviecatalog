package com.nandaprasetio.moviecatalog

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.ViewInteraction
import java.lang.reflect.Field
import org.hamcrest.Matcher

val Matcher<View>.getView: View?
    get() {
        return try {
            val viewInteraction: ViewInteraction = Espresso.onView(this)
            val finderField: Field = viewInteraction.javaClass.getDeclaredField("viewFinder")
            finderField.isAccessible = true
            val finder: ViewFinder = finderField.get(viewInteraction) as ViewFinder
            finder.view
        } catch (e: Exception) {
            null
        }
    }