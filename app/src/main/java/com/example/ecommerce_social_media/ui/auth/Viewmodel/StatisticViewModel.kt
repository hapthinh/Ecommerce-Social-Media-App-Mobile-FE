package com.example.ecommerce_social_media.ui.auth.Viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_social_media.data.entity.PostStatistic
import com.example.ecommerce_social_media.ui.repository.StatisticRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : ViewModel() {
    private val _postStatistics = MutableStateFlow<List<PostStatistic>>(emptyList())
    val postStatistics: StateFlow<List<PostStatistic>> = _postStatistics

    fun fetchPostStatisticsByMonthYear(month: Int, year: Int) {
        viewModelScope.launch {
            try {
                val statistics = statisticRepository.getPostStatistics(month, year)
                _postStatistics.value = statistics.map {
                    PostStatistic(it.month,it.year,it.productpostCount)
                }

            } catch (e: Exception) {
                Log.e("StatisticViewModel", "Error fetching statistics", e)
            }
        }
    }
}