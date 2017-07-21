package com.thoughtworks.pafsilva.testautomationworkshop.login;

import android.widget.EditText;

import com.thoughtworks.pafsilva.testautomationworkshop.BuildConfig;
import com.thoughtworks.pafsilva.testautomationworkshop.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    @Ignore(value = "This is a bug that need to be fixed in workshop pt2")
    public void shouldShowEmptyMessageErrorWhenEmailFieldIsEmpty() {
        EditText edtEmail = (EditText) loginActivity.findViewById(R.id.edtEmail);

        loginActivity.findViewById(R.id.btnLogin).performClick();

        assertEquals("This field is empty", edtEmail.getError());
    }
}