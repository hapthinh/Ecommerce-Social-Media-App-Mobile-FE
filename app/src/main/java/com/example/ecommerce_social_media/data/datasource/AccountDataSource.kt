package com.example.ecommerce_social_media.data.datasource

import com.example.ecommerce_social_media.data.entity.AccountResponse2
import retrofit2.Response

interface AccountDataSource {
    suspend fun getCurrentAccount() : Response<AccountResponse2>
}