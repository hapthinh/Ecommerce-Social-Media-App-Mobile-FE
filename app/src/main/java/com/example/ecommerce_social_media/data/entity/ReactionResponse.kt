package com.example.ecommerce_social_media.data.entity

data class ReactionResponse(
    val id: Int,
    val account: AccountResponse,
    val reaction: ReactionTypeResponse,
    val created_date: String?,
    val updated_date: String?,
    val deleted_date: String?,
    val active: Boolean,
    val product_post: Int
)