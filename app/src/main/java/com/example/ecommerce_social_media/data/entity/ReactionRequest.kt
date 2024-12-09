package com.example.ecommerce_social_media.data.entity

data class ReactionRequest(
    val reactionId: Int,
    val account: AccountResponse2,
    val product: ProductResponse,
    val post_content : String,
)