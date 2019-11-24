package com.thoughtworks.pafsilva.testautomationworkshop.userdetails

import android.os.Bundle
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.model.User

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val user = intent.extras?.get("user") as User
        val txtWelcomeMsg = findViewById<View>(R.id.txtWelcomeMsg) as TextView

        txtWelcomeMsg.text = user.name
    }
}
