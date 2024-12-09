package com.example.ecommerce_social_media.data.api

import android.content.SharedPreferences

class TokenProvider(private val sharedPreferences: SharedPreferences) {
    fun getToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    fun saveToken(token : String){
        sharedPreferences.edit().putString("jwt_token", token).apply()
    }

    fun clearToken() {
        sharedPreferences.edit().remove("jwt_token").apply()
    }
}