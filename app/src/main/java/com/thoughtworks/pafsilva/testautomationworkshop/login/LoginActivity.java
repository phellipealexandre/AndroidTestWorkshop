package com.thoughtworks.pafsilva.testautomationworkshop.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thoughtworks.pafsilva.testautomationworkshop.R;
import com.thoughtworks.pafsilva.testautomationworkshop.model.User;
import com.thoughtworks.pafsilva.testautomationworkshop.retrofit.APIEndpoints;
import com.thoughtworks.pafsilva.testautomationworkshop.userdetails.UserDetailsActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTitle;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtBntForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            doLogin();
        } else if (v.getId() == R.id.txtbtnForgotPassword){
            String url = "http://www.google.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    private void doLogin() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        boolean isValid = validateFields(email, password);

        if (isValid) {
            callLoginEndpoint(email, password);
        }
    }

    private boolean validateFields(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            edtEmail.setError("This field is empty");
            isValid = false;
        }

        if (password.isEmpty()) {
            edtPassword.setError("This field is empty");
            isValid = false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please fill your email correctly");
            isValid = false;
        }

        if (!Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$").matcher(password).matches()) {
            edtPassword.setError("Your password must have at least 8 characters with letters and numbers");
            isValid = false;
        }

        return isValid;
    }

    private void callLoginEndpoint(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIEndpoints APIEndpoints = retrofit.create(APIEndpoints.class);
        final Call<List<User>> user = APIEndpoints.getUser(email, password);

        user.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressDialog.dismiss();
                List<User> users = response.body();

                if (!users.isEmpty()) {
                    Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
                    intent.putExtra("user", users.get(0));
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Error")
                            .setMessage("User not found for this credentials")
                            .setPositiveButton("Ok", null)
                            .create();

                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressDialog.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Error")
                        .setMessage("Error occurred in Login")
                        .setPositiveButton("Ok", null)
                        .create();

                alertDialog.show();
            }
        });
    }

    private void initComponents() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtBntForgotPassword = (TextView) findViewById(R.id.txtbtnForgotPassword);

        btnLogin.setOnClickListener(this);
        txtBntForgotPassword.setOnClickListener(this);
    }
}
