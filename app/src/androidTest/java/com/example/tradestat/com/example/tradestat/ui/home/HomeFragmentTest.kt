package com.example.tradestat.com.example.tradestat.ui.home

import android.content.res.Configuration
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tradestat.MainActivity
import com.example.tradestat.R
import com.example.tradestat.data.model.Trade
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.ui.dateStatistic.DateActivity
import com.example.tradestat.ui.home.HomeViewModel
import com.example.tradestat.ui.usefullMaterials.MaterialsActivity
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun testDonutViewIsDisplayed() {
        onView(withId(R.id.donut_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testDonutTextIsDisplayed() {
        onView(withId(R.id.Donut_text)).check(matches(isDisplayed()))
    }

    @Test
    fun testShortNumberTextIsDisplayed() {
        onView(withId(R.id.short_number)).check(matches(isDisplayed()))
    }

    @Test
    fun testLongNumberTextIsDisplayed() {
        onView(withId(R.id.long_number)).check(matches(isDisplayed()))
    }

    @Test
    fun testDateCardNavigatesToDateActivity() {
        onView(withId(R.id.date_card)).perform(click())
        onView(withId(R.id.date_scroll_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testMaterialsCardNavigatesToMaterialsActivity() {
        onView(withId(R.id.materialsCard)).perform(click())
        onView(withId(R.id.materials_layout)).check(matches(isDisplayed()))
    }
    @Test
    fun localizationChange() {
        var currentLocale = Locale.getDefault().language
        when (currentLocale) {
            "ru" -> {
                // Проверяем элементы интерфейса на русском языке
                onView(withText("Статистика по времени")).check(matches(isDisplayed()))
            }
            "en" -> {
                // Проверяем элементы интерфейса на английском языке
                onView(withText("Time statistics")).check(matches(isDisplayed()))
            }
        }
        onView(withId(R.id.Language)).perform(click())
        var currentLocale1 = Locale.getDefault().language
        if (currentLocale == currentLocale1){
            assert(false)
        }
        when (currentLocale1) {
            "ru" -> {
                // Проверяем элементы интерфейса на русском языке
                onView(withText("Статистика по времени")).check(matches(isDisplayed()))
            }
            "en" -> {
                // Проверяем элементы интерфейса на английском языке
                onView(withText("Time statistics")).check(matches(isDisplayed()))
            }
        }

    }
    @Test
    fun checkThemeChange() {
        val initialTheme = getInitialTheme(activityScenarioRule.scenario)
        onView(withId(R.id.Theme)).perform(click())
        var secondTheme = getInitialTheme(activityScenarioRule.scenario)
        if (initialTheme != secondTheme){
            assert(true)
        }else{
            assert(false)
        }
    }
    private fun getInitialTheme(scenario: ActivityScenario<MainActivity>): Int {
        var initialTheme = 0
        scenario.onActivity { activity ->
            initialTheme = activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        }
        return initialTheme
    }
}