package com.thoughtworks.pafsilva.testautomationworkshop.login.services;

import com.thoughtworks.pafsilva.testautomationworkshop.BuildConfig;
import com.thoughtworks.pafsilva.testautomationworkshop.model.User;
import com.thoughtworks.pafsilva.testautomationworkshop.retrofit.APIEndpoints;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {

    private final Retrofit retrofit;

    public LoginService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.login_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void callAuthenticationService(Callback<List<User>> callback, String email, String password) {
        APIEndpoints APIEndpoints = retrofit.create(APIEndpoints.class);
        Call<List<User>> userCall = APIEndpoints.getUser(email, password);
        userCall.enqueue(callback);
    }
}
