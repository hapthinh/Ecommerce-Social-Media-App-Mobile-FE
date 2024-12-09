package com.example.ecommerce_social_media.data.datasource

import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.entity.AccountResponse2
import retrofit2.Response
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : AccountDataSource{
    override suspend fun getCurrentAccount(): Response<AccountResponse2> {
        return apiService.getCurrentAccount()
    }
}