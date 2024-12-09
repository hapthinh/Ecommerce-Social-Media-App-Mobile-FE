package com.example.ecommerce_social_media.data.entity

import com.squareup.moshi.Json

data class Post(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "post_content") val post_content: String,
)