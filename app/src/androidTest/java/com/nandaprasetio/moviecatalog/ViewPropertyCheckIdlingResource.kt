package com.nandaprasetio.moviecatalog

import android.view.View
import androidx.test.espresso.IdlingResource
import org.hamcrest.Matcher

class ViewPropertyCheckIdlingResource(
    private val viewMatcher: Matcher<View>,
    private val isIdle: (View) -> Boolean
): IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun isIdleNow(): Boolean {
        val view: View? = viewMatcher.getView
        val idle = view == null || isIdle(view)
        if (idle && resourceCallback != null) {
            resourceCallback?.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

    override fun getName(): String {
        return this.toString() + viewMatcher.toString()
    }
}