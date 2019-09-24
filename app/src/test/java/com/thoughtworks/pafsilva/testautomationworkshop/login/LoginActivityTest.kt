package com.thoughtworks.pafsilva.testautomationworkshop.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import kotlinx.android.synthetic.main.activity_login.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowNetworkInfo

@RunWith(RobolectricTestRunner::class)
class LoginActivityTest {

    private lateinit var loginActivity: LoginActivity

    @Before
    fun setUp() {
        loginActivity = Robolectric.setupActivity(LoginActivity::class.java)
    }

    @Test
    fun shouldShowEmptyMessageErrorWhenEmailFieldIsEmpty() {
        loginActivity.btnLogin.performClick()

        assertEquals("This field is empty", loginActivity.edtEmail.error)
    }

    @Test
    fun shouldShowEmptyMessageErrorWhenPasswordFieldIsEmpty() {
        loginActivity.btnLogin.performClick()

        assertEquals("This field is empty", loginActivity.edtPassword.error)
    }

    @Test
    fun shouldShowValidationMessageErrorWhenEmailFieldIsInvalid() {
        loginActivity.edtEmail.setText("tw@")

        loginActivity.btnLogin.performClick()

        assertEquals("Please fill your email correctly", loginActivity.edtEmail.error)
    }

    @Test
    fun shouldShowValidationMessageErrorWhenPasswordFieldIsInvalid() {
        loginActivity.edtPassword.setText("passwordWithoutNumbers")

        loginActivity.btnLogin.performClick()

        assertEquals(
            "Your password must have at least 8 characters with letters and numbers",
            loginActivity.edtPassword.error
        )
    }

    @Test
    fun shouldCallBrowserWhenForgotPasswordButtonIsClicked() {
        loginActivity.txtbtnForgotPassword.performClick()

        val shadowActivity = shadowOf(loginActivity)
        val intent = shadowActivity.nextStartedActivity

        assertEquals("http://www.google.com", intent.dataString)
        assertEquals(Intent.ACTION_VIEW, intent.action)
    }

    @Test
    fun shouldShowErrorDialogWhenThereIsNoInternet() {
        setConnectivity(false)
        loginActivity.edtEmail.setText("tw@thoughtworks.com")
        loginActivity.edtPassword.setText("passwordWithNumbers123456")

        loginActivity.btnLogin.performClick()

        assertNotNull(ShadowAlertDialog.getLatestDialog())
    }

    private fun setConnectivity(connectivity: Boolean) {
        val connectivityManager = loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val shadowConnectivityManager = shadowOf(connectivityManager)
        val networkInfo = ShadowNetworkInfo.newInstance(null, ConnectivityManager.TYPE_WIFI, 0, true, connectivity)
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo)
    }
}