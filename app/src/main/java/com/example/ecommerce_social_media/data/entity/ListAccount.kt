package com.example.ecommerce_social_media.data.entity

data class ListAccount(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<AccountResponse3>
)
