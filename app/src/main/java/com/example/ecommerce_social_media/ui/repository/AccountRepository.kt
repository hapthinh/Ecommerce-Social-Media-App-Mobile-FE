package com.example.ecommerce_social_media.ui.repository

import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.entity.AccountResponse3
import com.example.ecommerce_social_media.data.entity.ListAccount
import retrofit2.Response
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllAccount() : Response<ListAccount>{
        return apiService.getAccounts()
    }

    suspend fun addAccount(accountResponse3: AccountResponse3) : Response<Unit>{
        return apiService.addAccount(accountResponse3)
    }

    suspend fun updateAccount(id : Int,accountResponse3: AccountResponse3) : Response<Unit>{
        return apiService.updateAccount(id, accountResponse3)
    }

    suspend fun deleteAccount(id: Int) : Response<Unit>{
        return apiService.deleteAccount(id)
    }
}