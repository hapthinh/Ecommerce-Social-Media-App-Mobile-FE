package com.example.ecommerce_social_media.data.entity

import com.squareup.moshi.Json

data class PostStatistic(
    val month: Int,
    val year: Int,
    @Json(name = "productpost_count") val productpostCount: Int)

