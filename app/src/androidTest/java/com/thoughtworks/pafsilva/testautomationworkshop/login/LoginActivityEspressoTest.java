package com.thoughtworks.pafsilva.testautomationworkshop.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.thoughtworks.pafsilva.testautomationworkshop.R;
import com.thoughtworks.pafsilva.testautomationworkshop.utils.MockedServer;
import com.thoughtworks.pafsilva.testautomationworkshop.utils.ReadDummyServerResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void shouldShowTitleWhenOpenActivity() throws Exception {
        onView(withText("Login Screen")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowLoginButtonWhenOpenActivity() throws Exception {
        onView(withId(R.id.btnLogin)).check(matches(withText("Do login")));
    }

    @Test
    public void shouldShowEmptyMessageErrorWhenFieldsAreEmpty() throws Exception {
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.edtPassword)).check(matches(hasErrorText("This field is empty")));
        onView(withId(R.id.edtEmail)).check(matches(hasErrorText("This field is empty")));
    }

    @Test
    public void shouldShowValidationMessageErrorWhenEmailFieldIsInvalid() throws Exception {
        onView(withId(R.id.edtEmail)).perform(typeText("tw@"));
        onView(withId(R.id.edtPassword)).perform(typeText("CorrectPassword123456"));

        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.edtEmail)).check(matches(hasErrorText("Please fill your email correctly")));
    }

    @Test
    public void shouldShowValidationMessageErrorWhenPasswordFieldIsInvalid() throws Exception {
        onView(withId(R.id.edtEmail)).perform(typeText("tw@tw.com"));
        onView(withId(R.id.edtPassword)).perform(typeText("wrongPass"));

        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.edtPassword)).check(matches(hasErrorText("Your password must have at least 8 characters with letters and numbers")));
    }

    @Test
    public void shouldShowUserDetailsWhenLoginCompletesSuccessfully() throws Exception {
        MockedServer mockedServer = new MockedServer();
        mockedServer.start();
        mockedServer.setResponse(ReadDummyServerResponse.readJsonFile("loginresponse.json"));

        onView(withId(R.id.edtEmail)).perform(typeText("Sincere@april.biz"));
        onView(withId(R.id.edtPassword)).perform(typeText("CorrectPassword123456"));

        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.btnLogin)).perform(click());

        onView(withId(R.id.txtWelcomeMsg)).check(matches(withText("Leanne Test")));

        mockedServer.stop();
    }
}