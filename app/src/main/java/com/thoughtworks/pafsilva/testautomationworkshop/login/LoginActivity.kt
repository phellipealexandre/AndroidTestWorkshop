package com.thoughtworks.pafsilva.testautomationworkshop.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.view.View

import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginPresenter
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.LoginService
import com.thoughtworks.pafsilva.testautomationworkshop.model.User
import com.thoughtworks.pafsilva.testautomationworkshop.userdetails.UserDetailsActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter = LoginPresenterImpl(this, LoginService())

        btnLogin.setOnClickListener { loginPresenter.handleClick(it.id) }
        txtbtnForgotPassword.setOnClickListener { loginPresenter.handleClick(it.id) }
    }

    override fun navigateToBrowser() {
        val url = "http://www.google.com"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun setEmailEditTextError(errorMessage: String) {
        edtEmail.error = errorMessage
    }

    override fun setPasswordEditTextError(errorMessage: String) {
        edtPassword.error = errorMessage
    }

    override fun navigateToUserDetails(user: User) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

    override fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    override fun dismissLoading() {
        progressDialog.dismiss()
    }

    override fun showErrorDialog(errorMessage: String) {
        val alertDialog = AlertDialog.Builder(this@LoginActivity)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("Ok", null)
            .create()

        alertDialog.show()
    }

    override fun doLogin() {
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (isNetworkAvailable()) {
            loginPresenter.doLogin(email, password, emailValid)
        } else {
            val alertDialog = AlertDialog.Builder(this@LoginActivity)
                .setTitle("Error")
                .setMessage("Please, enable internet connection")
                .setPositiveButton("Ok", null)
                .create()

            alertDialog.show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}

