package com.example.ecommerce_social_media.data.entity

data class CommentResponse(
    val id: Int,
    val comment_img_url: String?,
    val account: AccountResponse,
    val comment_content: String
)
