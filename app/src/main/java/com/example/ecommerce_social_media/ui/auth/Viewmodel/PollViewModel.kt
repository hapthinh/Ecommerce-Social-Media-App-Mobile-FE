package com.example.ecommerce_social_media.ui.auth.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_social_media.data.entity.PollRequestDTO
import com.example.ecommerce_social_media.data.entity.PostRequestDTO
import com.example.ecommerce_social_media.data.entity.Post
import com.example.ecommerce_social_media.ui.repository.PollRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollViewModel @Inject constructor(
    private val pollRepository: PollRepository
) : ViewModel() {

    // Tạo Post trước
    fun createPost(postRequest: PostRequestDTO, onPostSuccess: (Post) -> Unit, onPostError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = pollRepository.createPost(postRequest)
                if (response.isSuccessful) {
                    response.body()?.let { createdPost ->
                        onPostSuccess(createdPost)
                    } ?: onPostError("Failed to create post: No data received.")
                } else {
                    onPostError("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                onPostError("Failed to create post: ${e.message}")
            }
        }
    }

    // Sau khi có postId, tạo Poll
    fun createPoll(pollRequest: PollRequestDTO, onPollSuccess: () -> Unit, onPollError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = pollRepository.createPoll(pollRequest)
                if (response.isSuccessful) {
                    onPollSuccess()
                } else {
                    onPollError("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                onPollError("Failed to create poll: ${e.message}")
            }
        }
    }
}
