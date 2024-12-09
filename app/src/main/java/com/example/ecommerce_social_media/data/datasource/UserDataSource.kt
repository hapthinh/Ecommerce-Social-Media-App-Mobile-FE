package com.example.ecommerce_social_media.data.datasource

import com.example.ecommerce_social_media.data.entity.UserResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response

interface UserDataSource {
    suspend fun getCurrentUser(): Response<UserResponse>
}