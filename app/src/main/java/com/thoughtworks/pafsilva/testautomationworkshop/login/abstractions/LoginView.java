package com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions;

import com.thoughtworks.pafsilva.testautomationworkshop.model.User;

public interface LoginView {
    void showLoading();
    void dismissLoading();
    void showErrorDialog(String errorMessage);
    void doLogin();
    void navigateToBrowser();
    void setEmailEditTextError(String errorMessage);
    void setPasswordEditTextError(String errorMessage);
    void navigateToUserDetails(User user);
}
