package com.thoughtworks.pafsilva.testautomationworkshop.login.services

import com.thoughtworks.pafsilva.testautomationworkshop.login.abstractions.LoginView
import com.thoughtworks.pafsilva.testautomationworkshop.model.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class UserLoginCallbackTest {

    private lateinit var userLoginCallback: UserLoginCallback

    @Mock
    private lateinit var loginView: LoginView

    @Mock
    private lateinit var user: User

    @Mock
    private lateinit var call: Call<List<User>>

    @Before
    fun setUp() {
        userLoginCallback = UserLoginCallback(loginView)
    }

    @Test
    fun dismissDialogWhenOperationIsSuccessful() {
        val response = Response.success(listOf<User>())

        userLoginCallback.onResponse(call, response)

        verify(loginView).dismissLoading()
    }

    @Test
    fun navigateToUserDetailsWhenResponseHasUser() {
        val users = listOf(user)
        val response = Response.success(users)

        userLoginCallback.onResponse(call, response)

        verify(loginView).navigateToUserDetails(user)
    }

    @Test
    fun showErrorDialogWhenResponseIsEmpty() {
        val response = Response.success(listOf<User>())

        userLoginCallback.onResponse(call, response)

        verify(loginView).showErrorDialog("User not found for this credentials")
    }

    @Test
    fun callDismissDialogOnFailure() {
        userLoginCallback.onFailure(call, Throwable())

        verify(loginView).dismissLoading()
    }

    @Test
    fun callErrorDialogOnFailure() {
        userLoginCallback.onFailure(call, Throwable())

        verify(loginView).showErrorDialog("Error occurred in Login")
    }
}