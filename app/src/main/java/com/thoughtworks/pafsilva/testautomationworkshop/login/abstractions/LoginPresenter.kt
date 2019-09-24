package com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions

interface LoginPresenter {
    fun handleClick(viewId: Int)
    fun doLogin(email: String, password: String, emailValid: Boolean)
}
