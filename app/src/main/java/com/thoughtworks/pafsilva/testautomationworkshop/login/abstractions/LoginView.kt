package com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions

import com.thoughtworks.pafsilva.testautomationworkshop.model.User

interface LoginView {
    fun showLoading()
    fun dismissLoading()
    fun showErrorDialog(errorMessage: String)
    fun doLogin()
    fun navigateToBrowser()
    fun setEmailEditTextError(errorMessage: String)
    fun setPasswordEditTextError(errorMessage: String)
    fun navigateToUserDetails(user: User)
}
