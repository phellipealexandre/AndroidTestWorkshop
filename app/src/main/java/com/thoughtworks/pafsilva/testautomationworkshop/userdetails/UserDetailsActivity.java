package com.thoughtworks.pafsilva.testautomationworkshop.userdetails;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.thoughtworks.pafsilva.testautomationworkshop.R;
import com.thoughtworks.pafsilva.testautomationworkshop.model.User;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        User user = (User) getIntent().getExtras().get("user");
        TextView txtWelcomeMsg = (TextView) findViewById(R.id.txtWelcomeMsg);

        txtWelcomeMsg.setText(user.getName());
    }
}
