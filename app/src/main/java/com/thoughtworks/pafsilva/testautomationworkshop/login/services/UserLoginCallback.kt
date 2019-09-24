package com.thoughtworks.pafsilva.testautomationworkshop.login.services

import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView
import com.thoughtworks.pafsilva.testautomationworkshop.model.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserLoginCallback(private val loginView: LoginView) : Callback<List<User>> {

    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        loginView.dismissLoading()
        response.body()?.let {
            if (it.isNotEmpty()) {
                loginView.navigateToUserDetails(it[0])
            } else {
                loginView.showErrorDialog("User not found for this credentials")
            }
        }
    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        loginView.dismissLoading()
        loginView.showErrorDialog("Error occurred in Login")
    }
}
