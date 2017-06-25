package com.thoughtworks.pafsilva.testautomationworkshop.login.services;

import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView;
import com.thoughtworks.pafsilva.testautomationworkshop.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginCallbackTest {

    private UserLoginCallback userLoginCallback;

    @Mock
    private LoginView loginView;

    @Mock
    private User user;

    @Before
    public void setUp() throws Exception {
        userLoginCallback = new UserLoginCallback(loginView);
    }

    @Test
    public void dismissDialogWhenOperationIsSuccessful() throws Exception {
        List<User> users = new ArrayList<>();
        Response<List<User>> response = Response.success(users);

        userLoginCallback.onResponse(null, response);

        verify(loginView).dismissLoading();
    }

    @Test
    public void navigateToUserDetailsWhenResponseHasUser() throws Exception {
        List<User> users = Collections.singletonList(user);
        Response<List<User>> response = Response.success(users);

        userLoginCallback.onResponse(null, response);

        verify(loginView).navigateToUserDetails(user);
    }

    @Test
    public void showErrorDialogWhenResponseIsEmpty() throws Exception {
        List<User> users = new ArrayList<>();
        Response<List<User>> response = Response.success(users);

        userLoginCallback.onResponse(null, response);

        verify(loginView).showErrorDialog("User not found for this credentials");
    }

    @Test
    public void callDismissDialogOnFailure() throws Exception {
        userLoginCallback.onFailure(null, null);

        verify(loginView).dismissLoading();
    }

    @Test
    public void callErrorDialogOnFailure() throws Exception {
        userLoginCallback.onFailure(null, null);

        verify(loginView).showErrorDialog("Error occurred in Login");
    }
}