package com.thoughtworks.pafsilva.testautomationworkshop.retrofit;

import com.thoughtworks.pafsilva.testautomationworkshop.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoints {

    @GET("/users")
    Call<List<User>> getUser(@Query("email") String email, @Query("password") String password);
}