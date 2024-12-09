package com.example.ecommerce_social_media.ui.repository

import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.entity.PollRequestDTO
import com.example.ecommerce_social_media.data.entity.PostRequestDTO
import com.example.ecommerce_social_media.data.entity.Post
import retrofit2.Response
import javax.inject.Inject

class PollRepository @Inject constructor(
    private val apiService: ApiService
) {
    // Tạo Post
    suspend fun createPost(postRequest: PostRequestDTO): Response<Post> {
        return apiService.createPost(postRequest)
    }

    // Tạo Poll
    suspend fun createPoll(pollRequest: PollRequestDTO): Response<Unit> {
        return apiService.createPoll(pollRequest)
    }
}
