package com.thoughtworks.pafsilva.testautomationworkshop.login;

import com.thoughtworks.pafsilva.testautomationworkshop.R;
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView;
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.LoginService;
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.UserLoginCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Matcher;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {

    private LoginPresenterImpl loginPresenter;

    @Mock
    private LoginView loginView;

    @Mock
    private LoginService loginService;

    @Before
    public void setUp() throws Exception {
        loginPresenter = new LoginPresenterImpl(loginView, loginService);
    }

    @Test
    public void doLoginWhenClickOnLoginButton() throws Exception {
        loginPresenter.handleClick(R.id.btnLogin);

        verify(loginView).doLogin();
    }

    @Test
    public void callExternalBrowserWhenClickOnForgotPasswordButton() throws Exception {
        loginPresenter.handleClick(R.id.txtbtnForgotPassword);

        verify(loginView).navigateToBrowser();
    }

    @Test
    public void callValidationErrorWhenEmailIsIncorrect() throws Exception {
        loginPresenter.doLogin("incorrect", "password", false);

        verify(loginView).setEmailEditTextError("Please fill your email correctly");
    }

    @Test
    public void callValidationErrorWhenEmailIsEmpty() throws Exception {
        loginPresenter.doLogin("", "password", false);

        verify(loginView).setEmailEditTextError("This field is empty");
    }

    @Test
    public void callValidationErrorWhenPasswordIsIncorrect() throws Exception {
        loginPresenter.doLogin("test@test.com", "password", true);

        verify(loginView).setPasswordEditTextError("Your password must have at least 8 characters with letters and numbers");
    }

    @Test
    public void callValidationErrorWhenPasswordlIsEmpty() throws Exception {
        loginPresenter.doLogin("test@test.com", "", true);

        verify(loginView).setPasswordEditTextError("This field is empty");
    }

    @Test
    public void showsLoadingWhenEmailAndPasswordAreCorrect() throws Exception {
        loginPresenter.doLogin("test@test.com", "passwordWith1234567Numbers", true);

        verify(loginView).showLoading();
    }

    @Test
    public void callServiceWhenEmailAndPasswordAreCorrect() throws Exception {
        loginPresenter.doLogin("test@test.com", "passwordWith1234567Numbers", true);

        verify(loginService).callAuthenticationService(any(UserLoginCallback.class),
                eq("test@test.com"), eq("passwordWith1234567Numbers"));
    }
}