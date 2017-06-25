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
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginPresenter;
import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView;
import com.thoughtworks.pafsilva.testautomationworkshop.login.services.LoginService;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private TextView txtTitle;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtBntForgotPassword;
    private ProgressDialog progressDialog;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        loginPresenter = new LoginPresenterImpl(this, new LoginService());
    }

    @Override
    public void onClick(View v) {
        loginPresenter.handleClick(v.getId());
    }

    @Override
    public void navigateToBrowser() {
        String url = "http://www.google.com";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void setEmailEditTextError(String errorMessage) {
        edtEmail.setError(errorMessage);
    }

    @Override
    public void setPasswordEditTextError(String errorMessage) {
        edtPassword.setError(errorMessage);
    }

    @Override
    public void navigateToUserDetails(User user) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void dismissLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .create();

        alertDialog.show();
    }

    @Override
    public void doLogin() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        loginPresenter.doLogin(email, password, emailValid);
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
