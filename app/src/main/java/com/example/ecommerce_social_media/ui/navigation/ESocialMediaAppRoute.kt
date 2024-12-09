package com.example.ecommerce_social_media.ui.navigation

import android.icu.number.ScientificNotation
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.ecommerce_social_media.data.entity.PostResponse

sealed class Screen {
    object SignUpScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
    object UserProfileScreen : Screen()
    object NewsFeedScreen : Screen()
    object PostScreen : Screen()
    object ManagePost : Screen()
    data class GetPostsByCategoryScreen(val categoryId: Int) : Screen()
    data class PostDetailScreen(val postId : Int) : Screen()
    object AdminHomeScreen : Screen()
    object UserManagementScreen : Screen()
    object PostManagementScreen : Screen()
    object CreatePollScreen : Screen()
    object StatisticScreen : Screen()
}

object ESocialMediaAppRoute{
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination: Screen){
        Log.d("ESocialMediaAppRoute","Navigating to: $destination")
        currentScreen.value = destination
    }
}