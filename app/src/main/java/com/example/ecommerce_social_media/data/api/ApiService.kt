package com.example.ecommerce_social_media.data.api

import com.example.ecommerce_social_media.data.entity.AccountResponse2
import com.example.ecommerce_social_media.data.entity.AccountResponse3
import com.example.ecommerce_social_media.data.entity.CategoryResponse
import com.example.ecommerce_social_media.data.entity.CommentRequest
import com.example.ecommerce_social_media.data.entity.ListAccount
import com.example.ecommerce_social_media.data.entity.LoginRequest
import com.example.ecommerce_social_media.data.entity.LoginResponse
import com.example.ecommerce_social_media.data.entity.PollRequestDTO
import com.example.ecommerce_social_media.data.entity.Post
import com.example.ecommerce_social_media.data.entity.PostListResponse
import com.example.ecommerce_social_media.data.entity.PostRequest
import com.example.ecommerce_social_media.data.entity.PostRequest2
import com.example.ecommerce_social_media.data.entity.PostRequestDTO
import com.example.ecommerce_social_media.data.entity.PostResponse
import com.example.ecommerce_social_media.data.entity.PostStatistic
import com.example.ecommerce_social_media.data.entity.ReactionRequest
import com.example.ecommerce_social_media.data.entity.UserRequest
import com.example.ecommerce_social_media.data.entity.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/login/")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("/signup/")
    suspend fun signup(
        @Body user: UserRequest
    ): Response<LoginResponse>

    @GET("users/current-user/")
    suspend fun getCurrentUser() : Response<UserResponse>

    @GET("accounts/current-product-posts/")
    suspend fun getCurrentPosts() : Response<PostListResponse>

    @GET("accounts/current-account/")
    suspend fun getCurrentAccount() : Response<AccountResponse2>

    @GET("categories/")
    suspend fun getCategories(): Response<List<CategoryResponse>>

    @GET("categories/get-posts-by-categories/{category_id}")
    suspend fun getPostByCategories(@Path("category_id") categoryId: Int) : Response<List<PostResponse>>

    @POST("product-posts/")
    suspend fun createPost(@Body postRequest: PostRequest2): Response<PostRequest2>

    @GET("new-feeds/")
    suspend fun getNewFeeds(
        @Query("page") page: Int,
        @Query("order_by") orderBy: String = "created_date",
        @Query("order_direction") orderDirection: String = "desc"
    ): Response<PostListResponse>

    @PATCH("product-posts/{postId}/like/")
    suspend fun addReaction(
        @Path("postId") postId: Int,
        @Body reactionRequest: ReactionRequest
    ): Response<Unit>

    @GET("product-posts/detail/{id}/")
    suspend fun getPostById(@Path("id") postId: Int): Response<PostResponse>

    @POST("product-posts/{postId}/comments/")
    suspend fun addComment(
        @Path("postId") postId: Int,
        @Body commentContent: CommentRequest
    ): Response<Unit>

    @DELETE("product-posts/{id}/")
    suspend fun deletedPost(@Path("id") postId: Int): Response<Unit>

    @PUT("product-posts/{id}/")
    suspend fun updatePost(updatedPost: PostResponse): Response<Unit>

    @GET("/accounts/")
    suspend fun getAccounts(): Response<ListAccount>

    @POST("/accounts/")
    suspend fun addAccount(@Body account: AccountResponse3): Response<Unit>

    @PUT("/accounts/{id}/")
    suspend fun updateAccount(@Path("id") id: Int, @Body account: AccountResponse3) : Response<Unit>

    @DELETE("/accounts/{id}/")
    suspend fun deleteAccount(@Path("id") id: Int) : Response<Unit>

    @POST("/posts/")
    suspend fun createPost(@Body postRequest: PostRequestDTO): Response<Post>

    // Tạo mới Poll với postId
    @POST("/polls/")
    suspend fun createPoll(@Body pollRequest: PollRequestDTO): Response<Unit>

    @GET("productposts/statistics")
    suspend fun getPostStatistics(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): List<PostStatistic>
}