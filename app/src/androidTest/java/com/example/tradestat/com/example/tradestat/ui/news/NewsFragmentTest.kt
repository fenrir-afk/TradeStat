package com.example.tradestat.com.example.tradestat.ui.news

import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tradestat.MainActivity
import com.example.tradestat.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun newsWasFoundAndAdded() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_news)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.editTextSearch))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


        Espresso.onView(ViewMatchers.withId(R.id.editTextSearch))
            .perform(ViewActions.typeText("Tesla"),
                ViewActions.pressImeActionButton()
            )
        Thread.sleep(14000)
        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            val cardsLayout = activity.findViewById<LinearLayout>(R.id.cardsLayout)
            assertThat(cardsLayout.childCount, greaterThan(0))
        }
    }
}