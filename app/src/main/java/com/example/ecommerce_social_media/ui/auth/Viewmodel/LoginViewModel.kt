package com.example.ecommerce_social_media.ui.auth.Viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_social_media.data.api.TokenProvider
import com.example.ecommerce_social_media.data.datasource.AccountDataSource
import com.example.ecommerce_social_media.data.entity.LoginRequest
import com.example.ecommerce_social_media.ui.events.LoginUIEvent
import com.example.ecommerce_social_media.ui.events.LoginUIState
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen
import com.example.ecommerce_social_media.ui.validation.Validation
import com.example.ecommerce_social_media.ui.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val context: Context,
    private val tokenProvider: TokenProvider,
    private val accountDataSource: AccountDataSource
) : ViewModel() {

    var loginUIState = mutableStateOf(LoginUIState())
    var passed = mutableStateOf(false)
    var loginInProcess = mutableStateOf(false)
    var loginError = mutableStateOf<String?>(null)
    var TAG = "LoginViewModel"
    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.UsernameChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    username = event.username
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                if (passed.value) {
                    login()
                } else {
                    Log.d(TAG, "Validation Failed")
                }
            }
        }
        validateData()
    }

    private fun login() {
        loginInProcess.value = true
        val username = loginUIState.value.username
        val password = loginUIState.value.password

        val loginRequest = LoginRequest(username = username, password = password)
        Log.d(TAG, "Login Request: $loginRequest")

        viewModelScope.launch {
            try {
                Log.d(TAG, "Username: $username, Password: $password")
                val response = authRepository.login(loginRequest)
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    Log.d(TAG, "Login Successful: Token ${tokenResponse?.access}")

                    tokenProvider.saveToken(tokenResponse?.access ?: "")
                    val accountResponse = accountDataSource.getCurrentAccount()

                    if (accountResponse.isSuccessful) {
                        val account = accountResponse.body()
                        if (account?.role?.id == 1) {
                            // Chuyển hướng đến trang admin
                            ESocialMediaAppRoute.navigateTo(Screen.AdminHomeScreen)
                        } else {
                            // Chuyển hướng đến trang người dùng bình thường
                            ESocialMediaAppRoute.navigateTo(Screen.HomeScreen)
                        }
                    }

                    val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putString("access_token", tokenResponse?.access)
                        apply()
                    }

                    Log.d(TAG,"thong tin tai khoan ${accountResponse}")
                } else {
                    Log.d(TAG, "Login Failed: ${response.message()}")
                    Log.d(TAG, "Response code: ${response.code()}, Response body: ${response.errorBody()?.string()}")
                    loginError.value = response.message()
                }
            } catch (e: HttpException) {
                Log.d(TAG, "HTTP error: ${e.localizedMessage}")
                loginError.value = "HTTP error: ${e.localizedMessage}"
            } finally {
                loginInProcess.value = false
            }
        }
    }

    private fun validateData() {
        val passwordResult = Validation.validatePassword(
            password = loginUIState.value.password
        )

        val usernameResult = Validation.validateUsername(
            username = loginUIState.value.username
        )

        passed.value = passwordResult.status && usernameResult.status
        loginUIState.value = loginUIState.value.copy(
            passwordError = passwordResult.status,
            usernameError = usernameResult.status
        )
    }
}
