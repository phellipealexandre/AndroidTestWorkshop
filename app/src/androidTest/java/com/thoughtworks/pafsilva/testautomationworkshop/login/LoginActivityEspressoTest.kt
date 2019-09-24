package com.thoughtworks.pafsilva.testautomationworkshop.login

import androidx.test.rule.ActivityTestRule

import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.utils.MockedServer
import com.thoughtworks.pafsilva.testautomationworkshop.utils.ReadDummyServerResponse

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class LoginActivityEspressoTest {

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun shouldShowTitleWhenOpenActivity() {
        onView(withText("Login Screen")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowLoginButtonWhenOpenActivity() {
        onView(withId(R.id.btnLogin)).check(matches(withText("Do login")))
    }

    @Test
    fun shouldShowEmptyMessageErrorWhenFieldsAreEmpty() {
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withId(R.id.edtPassword)).check(matches(hasErrorText("This field is empty")))
        onView(withId(R.id.edtEmail)).check(matches(hasErrorText("This field is empty")))
    }

    @Test
    fun shouldShowValidationMessageErrorWhenEmailFieldIsInvalid() {
        onView(withId(R.id.edtEmail)).perform(typeText("tw@"))
        onView(withId(R.id.edtPassword)).perform(typeText("CorrectPassword123456"))

        onView(withId(R.id.scrollView)).perform(swipeUp())
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withId(R.id.edtEmail)).check(matches(hasErrorText("Please fill your email correctly")))
    }

    @Test
    fun shouldShowValidationMessageErrorWhenPasswordFieldIsInvalid() {
        onView(withId(R.id.edtEmail)).perform(typeText("tw@tw.com"))
        onView(withId(R.id.edtPassword)).perform(typeText("wrongPass"))

        onView(withId(R.id.scrollView)).perform(swipeUp())
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withId(R.id.edtPassword)).check(matches(hasErrorText("Your password must have at least 8 characters with letters and numbers")))
    }

    @Test
    fun shouldShowUserDetailsWhenLoginCompletesSuccessfully() {
        val mockedServer = MockedServer()
        mockedServer.start()
        mockedServer.setResponse(ReadDummyServerResponse.readJsonFile("loginresponse.json"))

        onView(withId(R.id.edtEmail)).perform(typeText("Sincere@april.biz"))
        onView(withId(R.id.edtPassword)).perform(typeText("CorrectPassword123456"))

        onView(withId(R.id.scrollView)).perform(swipeUp())
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withId(R.id.txtWelcomeMsg)).check(matches(withText("Leanne Test")))

        mockedServer.stop()
    }
}