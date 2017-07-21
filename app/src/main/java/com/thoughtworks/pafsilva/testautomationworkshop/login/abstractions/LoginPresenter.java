package com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions;

public interface LoginPresenter {
    void handleClick(int viewId);
    void doLogin(String email, String password, boolean emailValid);
}
