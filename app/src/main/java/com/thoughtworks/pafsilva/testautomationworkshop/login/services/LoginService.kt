package com.thoughtworks.pafsilva.testautomationworkshop.login.services

import com.thoughtworks.pafsilva.testautomationworkshop.BuildConfig
import com.thoughtworks.pafsilva.testautomationworkshop.model.User
import com.thoughtworks.pafsilva.testautomationworkshop.retrofit.APIEndpoints
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginService {

    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.login_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun callAuthenticationService(callback: Callback<List<User>>, email: String, password: String) {
        val apiEndpoints = retrofit.create(APIEndpoints::class.java)
        val userCall = apiEndpoints.getUser(email, password)
        userCall.enqueue(callback)
    }
}
