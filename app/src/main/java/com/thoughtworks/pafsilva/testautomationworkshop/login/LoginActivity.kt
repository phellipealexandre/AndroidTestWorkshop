package com.thoughtworks.pafsilva.testautomationworkshop.login

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.model.User
import com.thoughtworks.pafsilva.testautomationworkshop.retrofit.APIEndpoints
import com.thoughtworks.pafsilva.testautomationworkshop.userdetails.UserDetailsActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponents()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnLogin) {
            doLogin()
        } else if (v.id == R.id.txtbtnForgotPassword) {
            val url = "http://www.google.com"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun doLogin() {
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

        val isValid = validateFields(email, password)

        if (isValid) {
            callLoginEndpoint(email, password)
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            edtEmail.error = "This field is empty"
            isValid = false
        }

        if (password.isEmpty()) {
            edtPassword.error = "This field is empty"
            isValid = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.error = "Please fill your email correctly"
            isValid = false
        }

        if (!Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$").matcher(password).matches()) {
            edtPassword.error =
                "Your password must have at least 8 characters with letters and numbers"
            isValid = false
        }

        return isValid
    }

    private fun callLoginEndpoint(email: String, password: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        progressDialog.isIndeterminate = true
        progressDialog.show()

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val APIEndpoints = retrofit.create(APIEndpoints::class.java)
        val user = APIEndpoints.getUser(email, password)

        user.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                progressDialog.dismiss()
                val users = response.body()

                if (users!!.isNotEmpty()) {
                    val intent = Intent(this@LoginActivity, UserDetailsActivity::class.java)
                    intent.putExtra("user", users[0])
                    this@LoginActivity.startActivity(intent)
                    this@LoginActivity.finish()
                } else {
                    val alertDialog = AlertDialog.Builder(this@LoginActivity)
                        .setTitle("Error")
                        .setMessage("User not found for this credentials")
                        .setPositiveButton("Ok", null)
                        .create()

                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                progressDialog.dismiss()
                val alertDialog = AlertDialog.Builder(this@LoginActivity)
                    .setTitle("Error")
                    .setMessage("Error occurred in Login")
                    .setPositiveButton("Ok", null)
                    .create()

                alertDialog.show()
            }
        })
    }

    private fun initComponents() {
        btnLogin.setOnClickListener(this)
        txtbtnForgotPassword.setOnClickListener(this)
    }
}
