package com.example.ecommerce_social_media.data.entity


data class PostResponse(
    val id: Int,
    val created_date: String?,
    val updated_date: String?,
    val deleted_date: String?,
    val active: Boolean,
    val post_content: String,
    val account: AccountResponse,
    val product: ProductResponse?,
    val comment: List<CommentResponse>,
    val reaction: List<ReactionResponse>,
    val post_image_url : String?,
    val reaction_count : Int,
    val comment_count : Int
)



