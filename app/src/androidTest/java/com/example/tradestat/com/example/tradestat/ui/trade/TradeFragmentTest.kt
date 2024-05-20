package com.example.tradestat.com.example.tradestat.ui.trade

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tradestat.MainActivity
import com.example.tradestat.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.anyOf
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TradeFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun addTradeToTheList() {
        onView(withId(R.id.navigation_trades)).perform(click())
        onView(withId(R.id.add_trade_fab))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_trade_fab)).perform(click())
        onView(withId(R.id.trade_dialog))
            .check(matches(isDisplayed()))
        onView(withId(R.id.instrument_field))
            .perform(ViewActions.typeText("Instrumen1"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.strategy_field))
            .perform(ViewActions.typeText("Strategy1"), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.daysSpinner)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
        onView(withId(R.id.daysSpinner))
            .check(matches(withSpinnerText(anyOf(equalTo("Sunday"), equalTo("Воскресенье")))))

        onView(withId(R.id.resultSpinner)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())

        onView(withId(R.id.resultSpinner))
            .check(matches(withSpinnerText(anyOf(equalTo("Victory"), equalTo("Победа")))))

        onView(withId(R.id.directionSpinner)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
        onView(withId(R.id.directionSpinner))
            .check(matches(withSpinnerText(containsString("Short"))))

        val scenario = activityScenarioRule.scenario
        onView(withId(R.id.add_button)).perform(click())

        scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
            MatcherAssert.assertThat(recyclerView.childCount, Matchers.greaterThan(0))
        }
    }
    @Test
    fun strategiesListDisplayed() {
        onView(withId(R.id.navigation_trades)).perform(click())
        for (i in 0..1){
            onView(withId(R.id.add_trade_fab))
                .check(matches(isDisplayed()))
            onView(withId(R.id.add_trade_fab)).perform(click())
            onView(withId(R.id.trade_dialog))
                .check(matches(isDisplayed()))
            onView(withId(R.id.instrument_field))
                .perform(ViewActions.typeText("Inst$i"), ViewActions.closeSoftKeyboard())
            onView(withId(R.id.strategy_field))
                .perform(ViewActions.typeText("Str$i"), ViewActions.closeSoftKeyboard())

            onView(withId(R.id.daysSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
            onView(withId(R.id.daysSpinner))
                .check(matches(withSpinnerText(anyOf(equalTo("Sunday"), equalTo("Воскресенье")))))

            onView(withId(R.id.resultSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())

            onView(withId(R.id.resultSpinner))
                .check(matches(withSpinnerText(anyOf(equalTo("Victory"), equalTo("Победа")))))

            onView(withId(R.id.directionSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
            onView(withId(R.id.directionSpinner))
                .check(matches(withSpinnerText(containsString("Short"))))
            onView(withId(R.id.add_button)).perform(click())
        }
        onView(withId(R.id.Strategy_card)).perform(click())
        onView(withId(R.id.strategy_sort_layout_id))
            .check(matches(isDisplayed()))
        onView(withText("Str1")).check(matches(isDisplayed()))
        onView(withText("Str0")).check(matches(isDisplayed()))
    }
    @Test
    fun instrumentsListDisplayed() {
        onView(withId(R.id.navigation_trades)).perform(click())
        for (i in 0..1){
            onView(withId(R.id.add_trade_fab))
                .check(matches(isDisplayed()))
            onView(withId(R.id.add_trade_fab)).perform(click())
            onView(withId(R.id.trade_dialog))
                .check(matches(isDisplayed()))
            onView(withId(R.id.instrument_field))
                .perform(ViewActions.typeText("Inst$i"), ViewActions.closeSoftKeyboard())
            onView(withId(R.id.strategy_field))
                .perform(ViewActions.typeText("Str$i"), ViewActions.closeSoftKeyboard())

            onView(withId(R.id.daysSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
            onView(withId(R.id.daysSpinner))
                .check(matches(withSpinnerText(anyOf(equalTo("Sunday"), equalTo("Воскресенье")))))

            onView(withId(R.id.resultSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())

            onView(withId(R.id.resultSpinner))
                .check(matches(withSpinnerText(anyOf(equalTo("Victory"), equalTo("Победа")))))


            onView(withId(R.id.directionSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
            onView(withId(R.id.directionSpinner))
                .check(matches(withSpinnerText(containsString("Short"))))
            onView(withId(R.id.add_button)).perform(click())
        }
        onView(withId(R.id.instrument_card)).perform(click())
        onView(withText("Inst1")).check(matches(isDisplayed()))
        onView(withText("Inst0")).check(matches(isDisplayed()))
    }
    @Test
    fun deleteTrade() {
        onView(withId(R.id.navigation_trades)).perform(click())
        onView(withId(R.id.add_trade_fab))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_trade_fab)).perform(click())
        onView(withId(R.id.trade_dialog))
            .check(matches(isDisplayed()))
        onView(withId(R.id.instrument_field))
            .perform(ViewActions.typeText("Instrumen1"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.strategy_field))
            .perform(ViewActions.typeText("Strategy1"), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.daysSpinner)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
        onView(withId(R.id.daysSpinner))
            .check(matches(withSpinnerText(anyOf(equalTo("Sunday"), equalTo("Воскресенье")))))

        onView(withId(R.id.resultSpinner)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())

        onView(withId(R.id.resultSpinner))
            .check(matches(withSpinnerText(anyOf(equalTo("Victory"), equalTo("Победа")))))

        onView(withId(R.id.directionSpinner)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
        onView(withId(R.id.directionSpinner))
            .check(matches(withSpinnerText(containsString("Short"))))

        onView(withId(R.id.add_button)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
        Thread.sleep(1000)
        onView(withId(R.id.deleteFab)).perform(click())

        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
            MatcherAssert.assertThat(recyclerView.childCount, Matchers.equalTo(0))
        }
    }
    @Test
    fun sortTrades() {
        onView(withId(R.id.navigation_trades)).perform(click())
        for (i in 0..1){
            onView(withId(R.id.add_trade_fab))
                .check(matches(isDisplayed()))
            onView(withId(R.id.add_trade_fab)).perform(click())
            onView(withId(R.id.trade_dialog))
                .check(matches(isDisplayed()))
            onView(withId(R.id.instrument_field))
                .perform(ViewActions.typeText("Inst$i"), ViewActions.closeSoftKeyboard())
            onView(withId(R.id.strategy_field))
                .perform(ViewActions.typeText("Str$i"), ViewActions.closeSoftKeyboard())

            onView(withId(R.id.daysSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
            onView(withId(R.id.daysSpinner))
                .check(matches(withSpinnerText(anyOf(equalTo("Sunday"), equalTo("Воскресенье")))))

            onView(withId(R.id.resultSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())

            onView(withId(R.id.resultSpinner))
                .check(matches(withSpinnerText(anyOf(equalTo("Victory"), equalTo("Победа")))))

            onView(withId(R.id.directionSpinner)).perform(click())
            onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click())
            onView(withId(R.id.directionSpinner))
                .check(matches(withSpinnerText(containsString("Short"))))
            onView(withId(R.id.add_button)).perform(click())
        }
        onView(withId(R.id.Date_card)).perform(click())
        onView(withId(R.id.recyclerView))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
        Thread.sleep(1000)
        onView(allOf(withId(R.id.deleteFab), isCompletelyDisplayed()))
            .perform(click())
        onView(withText("Str1")).check(matches(isDisplayed()))
    }

}