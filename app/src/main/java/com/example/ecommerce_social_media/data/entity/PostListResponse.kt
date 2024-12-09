package com.example.ecommerce_social_media.data.entity

data class PostListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PostResponse>
)

