package com.nandaprasetio.moviecatalog

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.nandaprasetio.moviecatalog.presentation.activity.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoInstrumentedTest {
    // Because using ActivityTestScenario (1.3.0) has issue,
    // therefore using ActivityTestRule temporarily until this issue is solved.
    // java.lang.AssertionError: Activity never becomes requested state "[STARTED, RESUMED, CREATED, DESTROYED]" (last lifecycle transition = "PRE_ON_CREATE")
    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loadMovieVideo() {
        waitLoadingDataAndScrollRecyclerViewToDown()
    }

    @Test
    fun loadDetailMovieVideo() {
        waitLoadingDataAndClickToDetailVideo()
    }

    @Test
    fun loadTvShowVideo() {
        onView(withText("TV SHOWS")).perform(click())
        waitLoadingDataAndScrollRecyclerViewToDown()
    }

    @Test
    fun loadDetailTvShowVideo() {
        onView(withText("TV SHOWS")).perform(click())
        waitLoadingDataAndClickToDetailVideo()
    }

    private fun waitLoadingDataAndScrollRecyclerViewToDown() {
        ViewPropertyIdlingResourceHelper.waitViewShown(withId(R.id.recycler_view_content))
        onView(withId(R.id.recycler_view_content)).check(matches(isDisplayed()))
        val recyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?
            = activityTestRule.activity.findViewById<RecyclerView>(R.id.recycler_view_content).adapter
        onView(withId(R.id.recycler_view_content)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                recyclerViewAdapter?.itemCount?.let {it - 1} ?: 0
            )
        )
    }

    private fun waitLoadingDataAndClickToDetailVideo() {
        ViewPropertyIdlingResourceHelper.waitViewShown(withId(R.id.recycler_view_content))
        onView(withId(R.id.recycler_view_content)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view_content)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        ViewPropertyIdlingResourceHelper.waitViewShown(withId(R.id.text_view_title))
        onView(withId(R.id.text_view_title)).check(matches(not(withText(""))))
        onView(withId(R.id.image_view_favourite)).also {
            var favouriteBefore: Int = -1
            it.check { view, _ -> favouriteBefore = view.tag as Int}
            val favouriteAfterExpected = when (favouriteBefore) {
                1 -> 0
                0 -> 1
                else -> error("Number must be 0 or 1")
            }
            it.perform(click())
            ViewPropertyIdlingResourceHelper.waitViewIntTagChanged(withId(R.id.image_view_favourite), favouriteAfterExpected)
        }
    }
}