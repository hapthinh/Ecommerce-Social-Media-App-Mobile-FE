package com.example.ecommerce_social_media.ui.auth.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_social_media.data.datasource.AccountDataSource
import com.example.ecommerce_social_media.data.entity.AccountResponse2
import com.example.ecommerce_social_media.data.entity.AccountResponse4
import com.example.ecommerce_social_media.data.entity.CategoryResponse
import com.example.ecommerce_social_media.data.entity.CommentRequest
import com.example.ecommerce_social_media.data.entity.CommentResponse
import com.example.ecommerce_social_media.data.entity.PostRequest
import com.example.ecommerce_social_media.data.entity.PostRequest2
import com.example.ecommerce_social_media.data.entity.PostResponse
import com.example.ecommerce_social_media.data.entity.ReactionRequest
import com.example.ecommerce_social_media.ui.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val accountDataSource: AccountDataSource
) : ViewModel() {

    private val _posts = MutableLiveData<List<PostResponse>>()
    val posts: LiveData<List<PostResponse>> get() = _posts

    private val _currentAccount = MutableLiveData<AccountResponse2>()
    val currentAccount: LiveData<AccountResponse2> get() = _currentAccount

    private val _currentAccount2 = MutableLiveData<AccountResponse2>()
    val currentAccount2: LiveData<AccountResponse2> get() = _currentAccount2

    private val _currentAccount4 = MutableLiveData<AccountResponse4>() // Thêm LiveData cho AccountResponse4
    val currentAccount4: LiveData<AccountResponse4> get() = _currentAccount4


    fun getCurrentPost() {
        viewModelScope.launch {
            val response = postRepository.getCurrentPosts()
            if (response.isSuccessful) {
                _posts.value = response.body()?.results ?: emptyList()
            } else {
                Log.d("PostViewModel ERROR", "error")
            }
        }
    }

    fun getCurrentAccount() {
        viewModelScope.launch {
            val response = accountDataSource.getCurrentAccount()
            if (response.isSuccessful) {
                _currentAccount.value = response.body()
                _currentAccount4.value = AccountResponse4(id = response.body()?.id ?: 0)
            }
        }
    }

    private val _categories = MutableLiveData<List<CategoryResponse>>()
    val categories: LiveData<List<CategoryResponse>> get() = _categories

    fun getCategories() {
        viewModelScope.launch {
            val response = postRepository.getCategories()
            if (response.isSuccessful) {
                _categories.value = response.body()
                Log.d("PostViewModel", "Danh sách danh mục: ${response.body()}")
            } else {
                Log.d("PostViewModel", "Lỗi khi lấy danh mục: ${response.message()}")
            }
        }
    }

    val createPostResponse = MutableLiveData<PostRequest2>()

    fun postNewPost(postRequest: PostRequest2) {
        viewModelScope.launch {
            try {
                val response = postRepository.createPost(postRequest) // Sử dụng postRepository
                if (response.isSuccessful) {
                    createPostResponse.postValue(response.body())
                }
            } catch (e: Exception) {
                // Xử lý lỗi
                Log.d("PostViewModel ERROR", e.message ?: "Unknown error")
            }
        }
    }

    private val _postsByCategory = MutableLiveData<List<PostResponse>>()
    val postsByCategory: LiveData<List<PostResponse>> = _postsByCategory

    fun getPostByCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = postRepository.getPostByCategoryID(categoryId)
                if (response.isSuccessful) {
                    _postsByCategory.value = response.body()
                }
            } catch (e: Exception) {
                Log.d("POSTVIEWMODEL", "GETPOSTBYCATEGORY ${e}")
            }
        }
    }

    private var currentPage = 1
    fun getPosts(page: Int) {
        viewModelScope.launch {
            try {
                val response = postRepository.getCurrentPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body()?.results ?: emptyList()
                }
            } catch (e: Exception) {
                Log.d("POSTVIEWMODEL", "GETPOSTS ${e}")
            }
        }
    }

    fun getNewFeedPosts(page: Int) {
        viewModelScope.launch {
            try {
                val response =
                    postRepository.getNewFeeds(page)
                if (response.isSuccessful) {
                    _posts.value = response.body()?.results ?: emptyList()
                    Log.d("PostViewModel", "Lấy bài đăng cho News Feed thành công")
                } else {
                    Log.d(
                        "PostViewModel",
                        "Lỗi khi lấy bài đăng cho News Feed: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                Log.d("PostViewModel", "Lỗi khi gọi API News Feed: ${e.message}")
            }
        }
    }

    private val _reactionStatus = MutableLiveData<Boolean>()
    val reactionStatus: LiveData<Boolean> get() = _reactionStatus

    private val _selectedPost = MutableLiveData<PostResponse?>()
    val selectedPost: LiveData<PostResponse?> get() = _selectedPost

    // ...

    fun selectPostForReaction(post: PostResponse) {
        _selectedPost.value = post
    }


    private val _selectedPostId = MutableLiveData<Int?>()
    val selectedPostId: LiveData<Int?> get() = _selectedPostId

    fun selectPostForReaction(postId: Int) {
        _selectedPostId.value = postId
    }

    fun toggleReaction(postId: Int, currentReactionId: Int) {
        viewModelScope.launch {
            try {
                Log.d("PostViewModel","is running")
                val accountResponse = accountDataSource.getCurrentAccount()
                if (accountResponse.isSuccessful) {
                    Log.d("PostViewModel","is running4")
                    Log.d("PostViewModel","is running ${_selectedPostId.value}")

                    val postId = _selectedPostId?.value
                    val postResponse = getPostById(postId ?: 1)
                    _currentAccount2.value = accountResponse.body()
                    if (postResponse != null) {
                            _selectedPost.value = postResponse
                        } else {
                            Log.d("PostViewModel", "Error fetching selected post by ID")
                            return@launch
                        }


                    val selectedPost = _selectedPost.value ?: return@launch
                    Log.d("PostViewModel", "Selected Post: $selectedPost")

                    Log.d("PostViewModel", "Selected post content: ${selectedPost.post_content}")
                    Log.d("PostViewModel", "Selected post account: ${selectedPost.account}")
                    Log.d("PostViewModel", "Selected post product: ${selectedPost.product}")

                    if (selectedPost.post_content.isNullOrEmpty() ||
                        selectedPost.account == null ||
                        selectedPost.product == null
                    ) {
                        Log.d("PostViewModel", "Missing required fields for reaction")
                        return@launch
                    }

                    val newReactionId = if (currentReactionId == 1) {
                        2
                    } else {
                        1
                    }
                    Log.d("PostViewModel", "update reaction")
                    Log.d("PostViewModel", "${_currentAccount2.value }")
                    val reactionRequest = ReactionRequest(
                        post_content = selectedPost.post_content,
                        account = _currentAccount2.value ?: return@launch,
                        reactionId = newReactionId,
                        product = selectedPost.product
                    )
                    Log.d("PostViewModel", "tao request")
                    // Gọi API thêm phản ứng (reaction)
                    val response = postRepository.addReaction(postId ?: 1, reactionRequest)
                    if (response.isSuccessful) {
                        Log.d("PostViewModel", "Reaction updated successfully")
                    } else {
                        Log.d("PostViewModel", "Error when updating reaction: ${response.message()}")
                    }
                } else {
                    Log.d("PostViewModel", "Error fetching account info")
                }
            } catch (e: Exception) {
                Log.d("PostViewModel", "Error in toggleReaction: ${e.message}")
            }
        }
    }

    private val _posts3 = MutableStateFlow<PostResponse?>(null)
    val posts3: StateFlow<PostResponse?> = _posts3.asStateFlow()

    suspend fun getPostById(postId: Int): PostResponse? {
        return try {
            val response = postRepository.getPostById(postId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.d("PostViewModel", "Error fetching post: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.d("PostViewModel", "Exception fetching post: ${e.message}")
            null
        }
    }


    val posts2 = MutableLiveData<List<PostResponse>>()




    fun addComment(postId: Int, commentContent: String) {
        viewModelScope.launch {
            try {
                Log.d("PostViewModel","addcomment is running")
                val accountResponse = accountDataSource.getCurrentAccount()
                if (accountResponse.isSuccessful) {

                    val currentAccount = accountResponse.body()
                    val postRespone = getPostById(postId)

                    val commentContent = CommentRequest(
                        comment_content = commentContent,
                        account = currentAccount!!.id,
                        post = postId
                    )
                    Log.d("addcomment","${commentContent}")
                    // Gọi API để thêm bình luận
                    val response = postRepository.addComment(
                        postId = postId,
                        commentContent
                    )

                    // Kiểm tra phản hồi từ server
                    if (response.isSuccessful) {
                        val newComment = response.body()
                        if (newComment != null) {

                            val updatedPosts = posts2.value?.map { post ->
                                if (post.id == postId) {
                                    post.copy(
                                        comment = post.comment + newComment as CommentResponse,
                                        comment_count = post.comment_count + 1
                                    )
                                } else post
                            }
                            posts2.postValue(updatedPosts!!)
                        } else {
                            Log.d("PostViewModel", "New comment is not of type CommentResponse")
                        }
                    } else {
                        Log.d("PostViewModel", "Error adding comment: ${response.message()}")
                    }
                } else {
                    Log.d("PostViewModel", "Error fetching account info")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error adding comment", e)
            }
        }
    }

    fun deletePost(postId: Int){
        viewModelScope.launch {
            val response = postRepository.deletePost(postId)
            if(response.isSuccessful){
                Log.d("Postviewmodel","deleted")
            }else {
                Log.d("Postviewmodel","delete error")
            }
        }
    }

    fun updatePost(updatedPost: PostResponse) {
        viewModelScope.launch {
            try {
                // Gọi API sửa bài viết
                val response = postRepository.updatePost(updatedPost)
                if (response.isSuccessful) {
                    Log.d("Postviewmodel","updated ")
                } else {
                    Log.d("Postviewmodel","update error ")

                }
            }catch (e: Exception) {

            }
        }
    }
}
