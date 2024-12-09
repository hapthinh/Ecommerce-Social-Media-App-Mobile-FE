package com.example.ecommerce_social_media.ui.auth.Viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_social_media.data.entity.AccountResponse3
import com.example.ecommerce_social_media.ui.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _accountList = MutableStateFlow<List<AccountResponse3>>(emptyList())
    val accountList = _accountList.asStateFlow()

    fun getAllAccounts() {
        viewModelScope.launch {
            try {
                val response = accountRepository.getAllAccount()
                if (response.isSuccessful){
                    val accountResponse = response.body()
                    val accounts = accountResponse?.results ?: emptyList()
                    _accountList.value = accounts
                }
            } catch (e:Exception){
                Log.e("AccountViewModel", "Exception: ${e.message}")
            }
        }
    }
    fun addAccount(accountResponse3: AccountResponse3){
        viewModelScope.launch {
            accountRepository.addAccount(accountResponse3)
            getAllAccounts()
        }
    }

    fun updateAccount(accountResponse3: AccountResponse3){
        viewModelScope.launch {
            accountRepository.updateAccount(accountResponse3.id, accountResponse3)
            getAllAccounts()
        }
    }

    fun deleteAccount(accountId : Int){
        viewModelScope.launch {
            accountRepository.deleteAccount(accountId)
            getAllAccounts()
        }
    }
}