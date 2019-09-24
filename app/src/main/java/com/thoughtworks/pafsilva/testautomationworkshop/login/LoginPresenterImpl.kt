package com.thoughtworks.pafsilva.testautomationworkshop.login

import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginPresenter
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.LoginService
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.UserLoginCallback

import java.util.regex.Pattern

class LoginPresenterImpl(private val loginView: LoginView, private val loginService: LoginService) : LoginPresenter {

    override fun handleClick(viewId: Int) {
        if (viewId == R.id.btnLogin) {
            loginView.doLogin()
        } else if (viewId == R.id.txtbtnForgotPassword) {
            loginView.navigateToBrowser()
        }
    }

    override fun doLogin(email: String, password: String, emailValid: Boolean) {
        val isValid = validateFields(email, password, emailValid)

        if (isValid) {
            callLoginEndpoint(email, password)
        }
    }

    private fun validateFields(email: String, password: String, emailValid: Boolean): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            loginView.setEmailEditTextError("This field is empty")
            isValid = false
        } else if (!emailValid) {
            loginView.setEmailEditTextError("Please fill your email correctly")
            isValid = false
        }

        if (password.isEmpty()) {
            loginView.setPasswordEditTextError("This field is empty")
            isValid = false
        } else if (!Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$").matcher(password).matches()) {
            loginView.setPasswordEditTextError("Your password must have at least 8 characters with letters and numbers")
            isValid = false
        }

        return isValid
    }

    private fun callLoginEndpoint(email: String, password: String) {
        loginView.showLoading()
        loginService.callAuthenticationService(UserLoginCallback(loginView), email, password)
    }
}
