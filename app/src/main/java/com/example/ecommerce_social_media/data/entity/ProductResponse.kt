package com.example.ecommerce_social_media.data.entity

data class ProductResponse (
    val id: Int,
    val product_name: String,
    val description: String,
    val price: String,
    val category: CategoryResponse
)

data class ProductResponse2(
    val name: String,
    val price: Double,
    val description: String
)
