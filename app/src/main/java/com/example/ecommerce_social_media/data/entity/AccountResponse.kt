package com.example.ecommerce_social_media.data.entity

data class AccountResponse (
    val id: Int,
    val user: UserResponse2,
    val avatar: String?
)

data class AccountResponse2 (
    val id: Int,
    val user: UserResponse2,
    val role : RoleResponse
)

data class AccountResponse3(
    val id: Int,
    val avatar: String?,
    val role: RoleResponse2,
    val user: UserResponse3,
    val phone_number: String?,
    val account_status: Boolean,
    val gender: Boolean
)

data class AccountResponse4(
    val id: Int
)