package com.thoughtworks.pafsilva.testautomationworkshop.login.services;

import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView;
import com.thoughtworks.pafsilva.testautomationworkshop.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginCallback implements Callback<List<User>> {

    private LoginView loginView;

    public UserLoginCallback(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        loginView.dismissLoading();
        List<User> users = response.body();

        if (!users.isEmpty()) {
            loginView.navigateToUserDetails(users.get(0));
        } else {
            loginView.showErrorDialog("User not found for this credentials");
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        loginView.dismissLoading();
        loginView.showErrorDialog("Error occurred in Login");
    }
}
