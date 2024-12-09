package com.example.ecommerce_social_media.data.datasource

import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.api.TokenProvider
import com.example.ecommerce_social_media.data.entity.UserResponse
import retrofit2.Response
import javax.inject.Inject


class UserDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : UserDataSource {

    override suspend fun getCurrentUser(): Response<UserResponse> {
        return apiService.getCurrentUser()
    }
}