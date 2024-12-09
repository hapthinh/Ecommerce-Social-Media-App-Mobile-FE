package com.example.ecommerce_social_media.ui.repository

import com.example.ecommerce_social_media.data.api.ApiService
import com.example.ecommerce_social_media.data.entity.CategoryResponse
import com.example.ecommerce_social_media.data.entity.CommentRequest
import com.example.ecommerce_social_media.data.entity.CommentResponse
import com.example.ecommerce_social_media.data.entity.PostListResponse
import com.example.ecommerce_social_media.data.entity.PostRequest
import com.example.ecommerce_social_media.data.entity.PostRequest2
import com.example.ecommerce_social_media.data.entity.PostResponse
import com.example.ecommerce_social_media.data.entity.ReactionRequest
import retrofit2.Response
import retrofit2.http.POST
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getCategories(): Response<List<CategoryResponse>> {
        return apiService.getCategories()
    }

    suspend fun createPost(postRequest: PostRequest2): Response<PostRequest2> {
        return apiService.createPost(postRequest)
    }

    suspend fun getCurrentPosts(): Response<PostListResponse> {
        return apiService.getCurrentPosts()
    }

    suspend fun getPostByCategoryID(categoryId: Int) : Response<List<PostResponse>>{
        return apiService.getPostByCategories(categoryId)
    }

    suspend fun getNewFeeds(page : Int): Response<PostListResponse> {
        return apiService.getNewFeeds(page)
    }

    suspend fun addReaction(postId: Int, reactionRequest: ReactionRequest) : Response<Unit> {
        return apiService.addReaction(postId,reactionRequest)
    }

    suspend fun getPostById(postId: Int): Response<PostResponse> {
        return apiService.getPostById(postId)
    }

    suspend fun addComment(postId: Int, commentContent: CommentRequest): Response<Unit> {
        return apiService.addComment(postId, commentContent)
    }

    suspend fun deletePost(postId: Int) : Response<Unit> {
        return apiService.deletedPost(postId)
    }

    suspend fun updatePost(updatedPost: PostResponse) : Response<Unit> {
        return apiService.updatePost(updatedPost)
    }
}