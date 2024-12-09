package com.example.ecommerce_social_media.data.entity

data class UserResponse (
    val id: Int,
    val last_login: String?,
    val is_superuser: Boolean,
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val is_staff: Boolean,
    val is_active: Boolean,
    val date_joined: String,
    val confirm_status: Int,
    val groups: List<Any>,
    val user_permissions: List<Any>
)

data class UserResponse2(
    val id: Int,
    val username: String
)

data class UserResponse3(
    val id: Int,
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String
)