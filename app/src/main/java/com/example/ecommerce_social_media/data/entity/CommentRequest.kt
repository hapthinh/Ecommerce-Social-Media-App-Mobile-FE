package com.example.ecommerce_social_media.data.entity

data class CommentRequest(
    val comment_content : String,
    val account : Int,
    val post : Int
)
