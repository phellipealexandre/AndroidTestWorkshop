package com.thoughtworks.pafsilva.testautomationworkshop.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;

import com.thoughtworks.pafsilva.testautomationworkshop.BuildConfig;
import com.thoughtworks.pafsilva.testautomationworkshop.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    public void shouldShowEmptyMessageErrorWhenEmailFieldIsEmpty() {
        EditText edtEmail = (EditText) loginActivity.findViewById(R.id.edtEmail);

        loginActivity.findViewById(R.id.btnLogin).performClick();

        assertEquals("This field is empty", edtEmail.getError());
    }

    @Test
    public void shouldShowEmptyMessageErrorWhenPasswordFieldIsEmpty() {
        EditText edtPassword = (EditText) loginActivity.findViewById(R.id.edtPassword);

        loginActivity.findViewById(R.id.btnLogin).performClick();

        assertEquals("This field is empty", edtPassword.getError());
    }

    @Test
    public void shouldShowValidationMessageErrorWhenEmailFieldIsInvalid() {
        EditText edtEmail = (EditText) loginActivity.findViewById(R.id.edtEmail);
        edtEmail.setText("tw@");

        loginActivity.findViewById(R.id.btnLogin).performClick();

        assertEquals("Please fill your email correctly", edtEmail.getError());
    }

    @Test
    public void shouldShowValidationMessageErrorWhenPasswordFieldIsInvalid() {
        EditText edtPassword = (EditText) loginActivity.findViewById(R.id.edtPassword);
        edtPassword.setText("passwordWithoutNumbers");

        loginActivity.findViewById(R.id.btnLogin).performClick();

        assertEquals("Your password must have at least 8 characters with letters and numbers", edtPassword.getError());
    }

    @Test
    public void shouldCallBrowserWhenForgotPasswordButtonIsClicked() {
        loginActivity.findViewById(R.id.txtbtnForgotPassword).performClick();

        ShadowActivity shadowActivity = shadowOf(loginActivity);
        Intent intent = shadowActivity.getNextStartedActivity();

        assertEquals("http://www.google.com", intent.getDataString());
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
    }

    @Test
    public void shouldShowErrorDialogWhenThereIsNoInternet() {
        setConnectivity(false);
        EditText edtEmail = (EditText) loginActivity.findViewById(R.id.edtEmail);
        EditText edtPassword = (EditText) loginActivity.findViewById(R.id.edtPassword);
        edtEmail.setText("tw@thoughtworks.com");
        edtPassword.setText("passwordWithNumbers123456");

        loginActivity.findViewById(R.id.btnLogin).performClick();

        assertNotNull(ShadowAlertDialog.getLatestDialog());
    }

    private void setConnectivity(boolean connectivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        ShadowConnectivityManager shadowConnectivityManager = shadowOf(connectivityManager);
        NetworkInfo networkInfo = ShadowNetworkInfo.newInstance(null, ConnectivityManager.TYPE_WIFI, 0, true, connectivity);
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);
    }
}