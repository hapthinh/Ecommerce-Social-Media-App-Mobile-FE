package com.example.ecommerce_social_media.ui.repository

import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.entity.LoginRequest
import com.example.ecommerce_social_media.data.entity.LoginResponse
import com.example.ecommerce_social_media.data.entity.UserRequest
import com.example.ecommerce_social_media.data.entity.UserResponse
import com.example.utilities.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(loginRequest : LoginRequest) : Response<LoginResponse> {
        return apiService.login(loginRequest)
    }

    suspend fun signup(userRequest: UserRequest) :  Response<LoginResponse> {
        return apiService.signup(userRequest)
    }
}