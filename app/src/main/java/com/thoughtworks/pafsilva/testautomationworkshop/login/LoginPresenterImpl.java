package com.thoughtworks.pafsilva.testautomationworkshop.login;

import com.thoughtworks.pafsilva.testautomationworkshop.R;
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginPresenter;
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView;
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.LoginService;
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.UserLoginCallback;

import java.util.regex.Pattern;

public class LoginPresenterImpl implements LoginPresenter {

    private final LoginService loginService;
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView, LoginService loginService) {
        this.loginView = loginView;
        this.loginService = loginService;
    }

    @Override
    public void handleClick(int viewId) {
        if (viewId == R.id.btnLogin) {
            loginView.doLogin();
        } else if (viewId == R.id.txtbtnForgotPassword) {
            loginView.navigateToBrowser();
        }
    }

    @Override
    public void doLogin(String email, String password, boolean emailValid) {
        boolean isValid = validateFields(email, password, emailValid);

        if (isValid) {
            callLoginEndpoint(email, password);
        }
    }

    private boolean validateFields(String email, String password, boolean emailValid) {
        boolean isValid = true;

        if (email.isEmpty()) {
            loginView.setEmailEditTextError("This field is empty");
            isValid = false;
        } else if (!emailValid) {
            loginView.setEmailEditTextError("Please fill your email correctly");
            isValid = false;
        }

        if (password.isEmpty()) {
            loginView.setPasswordEditTextError("This field is empty");
            isValid = false;
        } else if (!Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$").matcher(password).matches()) {
            loginView.setPasswordEditTextError("Your password must have at least 8 characters with letters and numbers");
            isValid = false;
        }

        return isValid;
    }

    private void callLoginEndpoint(String email, String password) {
        loginView.showLoading();
        loginService.callAuthenticationService(new UserLoginCallback(loginView), email, password);
    }
}
