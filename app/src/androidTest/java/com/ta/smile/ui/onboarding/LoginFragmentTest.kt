package com.ta.smile.ui.onboarding

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import com.ta.smile.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginFragmentTest {
    private val email = "luthfinsz@gmail.com"
    private val pass = "1234567890"

    @Before
    fun setup(){
        ActivityScenario.launch(OnboardingActivity::class.java)
    }

    @Test
    fun assertEmailPass(){
        onView(withId(R.id.til_email_log)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.til_pass_log)).perform(typeText(pass), closeSoftKeyboard())
        // To check is the button login is displayed or not
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).perform(click())
        // To check if the login feature is working or not
        onView(withId(R.id.pc_result_feces)).check(matches(isDisplayed()))
    }
}