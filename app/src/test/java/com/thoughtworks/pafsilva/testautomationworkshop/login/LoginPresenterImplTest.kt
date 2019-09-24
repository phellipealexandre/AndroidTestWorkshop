package com.thoughtworks.pafsilva.testautomationworkshop.login

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.LoginService
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.UserLoginCallback
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginPresenterImplTest {

    private lateinit var loginPresenter: LoginPresenterImpl

    @Mock
    private lateinit var loginView: LoginView

    @Mock
    private lateinit var loginService: LoginService

    @Before
    fun setUp() {
        loginPresenter = LoginPresenterImpl(loginView, loginService)
    }

    @Test
    fun doLoginWhenClickOnLoginButton() {
        loginPresenter.handleClick(R.id.btnLogin)

        verify(loginView).doLogin()
    }

    @Test
    fun callExternalBrowserWhenClickOnForgotPasswordButton() {
        loginPresenter.handleClick(R.id.txtbtnForgotPassword)

        verify(loginView).navigateToBrowser()
    }

    @Test
    fun callValidationErrorWhenEmailIsIncorrect() {
        loginPresenter.doLogin("incorrect", "password", false)

        verify(loginView).setEmailEditTextError("Please fill your email correctly")
    }

    @Test
    fun callValidationErrorWhenEmailIsEmpty() {
        loginPresenter.doLogin("", "password", false)

        verify(loginView).setEmailEditTextError("This field is empty")
    }

    @Test
    fun callValidationErrorWhenPasswordIsIncorrect() {
        loginPresenter.doLogin("test@test.com", "password", true)

        verify(loginView).setPasswordEditTextError("Your password must have at least 8 characters with letters and numbers")
    }

    @Test
    fun callValidationErrorWhenPasswordIsEmpty() {
        loginPresenter.doLogin("test@test.com", "", true)

        verify(loginView).setPasswordEditTextError("This field is empty")
    }

    @Test
    fun showsLoadingWhenEmailAndPasswordAreCorrect() {
        loginPresenter.doLogin("test@test.com", "passwordWith1234567Numbers", true)

        verify(loginView).showLoading()
    }

    @Test
    fun callServiceWhenEmailAndPasswordAreCorrect() {
        loginPresenter.doLogin("test@test.com", "passwordWith1234567Numbers", true)

        verify(loginService).callAuthenticationService(
            any<UserLoginCallback>(),
            eq("test@test.com"),
            eq("passwordWith1234567Numbers")
        )
    }
}