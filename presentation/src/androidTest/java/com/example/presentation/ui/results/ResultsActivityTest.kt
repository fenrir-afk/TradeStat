package com.example.presentation.ui.results

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.presentation.R
import org.junit.Rule
import org.junit.Test

class ResultsActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(ResultsActivity::class.java)
    @Test
    fun resultGraphDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.chart))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    fun ratingCardDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.ratingCard))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    fun amountCardDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.amountCard))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}