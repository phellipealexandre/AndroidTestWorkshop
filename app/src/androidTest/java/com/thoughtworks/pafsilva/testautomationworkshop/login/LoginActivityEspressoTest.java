package com.thoughtworks.pafsilva.testautomationworkshop.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.thoughtworks.pafsilva.testautomationworkshop.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
}