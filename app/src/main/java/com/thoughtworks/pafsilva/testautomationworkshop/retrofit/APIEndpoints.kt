package com.thoughtworks.pafsilva.testautomationworkshop.retrofit

import com.thoughtworks.pafsilva.testautomationworkshop.model.User

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndpoints {

    @GET("/users")
    fun getUser(@Query("email") email: String, @Query("password") password: String): Call<List<User>>
}