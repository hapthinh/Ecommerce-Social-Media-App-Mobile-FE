package com.example.ecommerce_social_media.ui.repository

import com.example.ecommerce_social_media.data.api.ApiService
import javax.inject.Inject

class StatisticRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPostStatistics(month: Int, year: Int) = apiService.getPostStatistics(month, year)
}