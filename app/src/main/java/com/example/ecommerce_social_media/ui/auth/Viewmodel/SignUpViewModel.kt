package com.example.ecommerce_social_media.ui.auth.Viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_social_media.data.entity.LoginResponse
import com.example.ecommerce_social_media.data.entity.UserRequest
import com.example.ecommerce_social_media.data.entity.UserResponse
import com.example.ecommerce_social_media.ui.events.SignUpUIEvent
import com.example.ecommerce_social_media.ui.events.SignUpUIState
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen
import com.example.ecommerce_social_media.ui.repository.AuthRepository
import com.example.ecommerce_social_media.ui.validation.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel()
{
    var signupUIState = mutableStateOf(SignUpUIState())
    var passed = mutableStateOf(false)
    var signupInProgress = mutableStateOf(false)

    fun onEvent(event: SignUpUIEvent){
        when(event){
            is SignUpUIEvent.UserNameChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    username = event.username
                )
            }
            is SignUpUIEvent.PasswordChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    password = event.password
                )
            }
            is SignUpUIEvent.ConfirmPasswordChanged -> {
                signupUIState.value = signupUIState.value.copy(
                    confirmpassword = event.confirmpassword
                )
            }
            SignUpUIEvent.SignUpButtonClicked -> {
                signup()
            }
        }
        validateData()
    }

    fun signup(){
        Log.d(TAG, "Inside_signUp")

        if(!passed.value){
            Log.d(TAG, "Validation failed")
            return
        }

        signupInProgress.value = true

        val user = UserRequest(
            username = signupUIState.value.username,
            password = signupUIState.value.password
        )

        try {
            viewModelScope.launch {
                val response : Response<LoginResponse> = authRepository.signup(user)
                signupInProgress.value = false
                if(response.isSuccessful){
                    Log.d(TAG, "Sign Up Successful")
                    ESocialMediaAppRoute.navigateTo(Screen.LoginScreen)
                }else{
                    Log.d(TAG, "Sign Up Failed : ${response.message()}")
                }
            }
        } catch (e: Exception){
            Log.d(TAG,"Error : ${e.localizedMessage}")
        }
    }

    fun validateData(){
        val usernameResult = Validation.validateUsername(
            username = signupUIState.value.username
        )
        val passwordResult = Validation.validatePassword(
            password = signupUIState.value.password
        )

        val confirmpasswordResult = Validation.validateConfirmPassword(
            password = signupUIState.value.password,
            confirmpassword = signupUIState.value.confirmpassword
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "usernameResult= ${usernameResult}")
        Log.d(TAG, "usernameResult= ${passwordResult}")
        Log.d(TAG, "usernameResult= ${confirmpasswordResult}")

        passed.value = usernameResult.status && passwordResult.status && confirmpasswordResult.status

        signupUIState.value = signupUIState.value.copy(
            usernameError = usernameResult.status,
            passwordError = passwordResult.status,
            confirmpasswordError = confirmpasswordResult.status
        )
    }
    companion object {
        val TAG = SignUpViewModel::class.simpleName
    }
}