package com.example.presentation.ui.notes


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation.R
import com.example.presentation.ui.notes.NoteActivity
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NotesActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(NoteActivity::class.java)

    @Test
    fun deleteNote() {
        onView(withId(R.id.notes_activity)).check(matches(isDisplayed()))
        onView(withId(R.id.add_note_fab)).perform(click())
        onView(withId(R.id.noteList)).check(matches(isDisplayed()))
        onView(withId(R.id.noteList))
            .check(matches(hasDescendant(isDisplayed())))
        onView(withId(R.id.deleteFab)).perform(click())
        Thread.sleep(2000)
        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            val cardsLayout = activity.findViewById<RecyclerView>(R.id.noteList)
            MatcherAssert.assertThat(cardsLayout.childCount, Matchers.equalTo(0))
        }

    }
    @Test
    fun createNote() {
        onView(withId(R.id.notes_activity)).check(matches(isDisplayed()))
        onView(withId(R.id.add_note_fab)).perform(click())
        onView(withId(R.id.noteList)).check(matches(isDisplayed()))
        onView(withId(R.id.noteList))
            .check(matches(hasDescendant(isDisplayed())))
        onView(withId(R.id.deleteFab)).perform(click())
    }

}