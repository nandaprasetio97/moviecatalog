package com.nandaprasetio.moviecatalog

import android.view.View
import android.widget.ImageView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

object ViewPropertyIdlingResourceHelper {
    fun waitViewShown(matcher: Matcher<View>) {
        ViewPropertyCheckIdlingResource(matcher) { it.isShown }.also {
            waitIdlingResourceUntilIdle(it) {
                onView(matcher).check(matches(ViewMatchers.isDisplayed()))
            }
        }
    }

    fun waitViewIntTagChanged(matcher: Matcher<View>, expectedInt: Int) {
        ViewPropertyCheckIdlingResource(matcher) { (it.tag as Int?) == expectedInt }
            .also {
            waitIdlingResourceUntilIdle(it) {
                onView(matcher).check { view, _ ->
                    if (view !is ImageView) {
                        error("View must be ImageView.")
                    }
                    if (view.tag != expectedInt) {
                        error("Not expected value.")
                    }
                }
            }
        }
    }

    private fun waitIdlingResourceUntilIdle(idlingResource: IdlingResource, action: () -> Unit) {
        try {
            IdlingRegistry.getInstance().register(idlingResource)
            action()
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
    }
}