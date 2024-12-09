package com.example.ecommerce_social_media.data.entity

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)