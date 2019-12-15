package com.rmalan.app.footballleague.activity

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rmalan.app.footballleague.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun searchMatchTest() {
        Thread.sleep(300)
        onView(withId(R.id.search)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())
        Thread.sleep(300)
        onView(withId(R.id.search_src_text)).perform(
            replaceText("Liverpool"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        Thread.sleep(3000)
        onView(withId(R.id.search_src_text)).perform(pressImeActionButton(), closeSoftKeyboard())
        Thread.sleep(5000)
    }
}