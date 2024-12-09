package com.example.ecommerce_social_media.ui.auth.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecommerce_social_media.data.datasource.UserDataSource
import com.example.ecommerce_social_media.data.entity.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataSource: UserDataSource
) : ViewModel() {
    private val _userResponse = MutableLiveData<UserResponse?>()
    val userResponse: LiveData<UserResponse?> = _userResponse

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val response = userDataSource.getCurrentUser()
                if (response.isSuccessful) {
                    _userResponse.value = response.body()
                } else {
                    _userResponse.value = null
                }
            } catch (e: Exception) {
                _userResponse.value = null
            }
        }
    }
}