package com.example.ecommerce_social_media.data.entity

data class PollRequestDTO(
    val title: String,
    val start_time: String,
    val end_time: String,
    val postId: Int? // Chỉ sử dụng postId, không phải đối tượng Post
)
data class PollResponseDTO(
    val id: Int,
    val title: String,
    val start_time: String,
    val end_time: String,
    val is_closed: Boolean,
    val postId: Int // Chứa postId liên kết với Poll
)
data class PostRequestDTO(
    val post_content: String
)
