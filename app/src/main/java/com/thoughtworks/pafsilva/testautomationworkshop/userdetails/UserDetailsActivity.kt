package com.thoughtworks.pafsilva.testautomationworkshop.userdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

import com.thoughtworks.pafsilva.testautomationworkshop.R
import com.thoughtworks.pafsilva.testautomationworkshop.model.User
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val user = intent.extras!!.get("user") as User
        txtWelcomeMsg.text = user.name
    }
}
