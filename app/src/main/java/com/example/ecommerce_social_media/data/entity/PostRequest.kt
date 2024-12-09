package com.example.ecommerce_social_media.data.entity

data class PostRequest (
    val post_content: String,
    val account: AccountResponse2,
    val product: ProductResponse
)

data class PostRequest2 (
    val post_content: String,
    val account: AccountResponse4,
    val product: ProductResponse
)