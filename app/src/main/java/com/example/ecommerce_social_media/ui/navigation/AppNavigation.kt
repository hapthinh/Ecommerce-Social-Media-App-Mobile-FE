package com.example.ecommerce_social_media.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.newfeed.NewFeedScreen
import com.example.ecommerce_social_media.ui.screens.admin.AdminHomeScreen
import com.example.ecommerce_social_media.ui.screens.admin.CreatePollScreen
import com.example.ecommerce_social_media.ui.screens.admin.PostManagementScreen
import com.example.ecommerce_social_media.ui.screens.admin.StatisticScreen
import com.example.ecommerce_social_media.ui.screens.admin.UserManagementScreen
import com.example.ecommerce_social_media.ui.screens.auth.GetPostByCategoryScreen
import com.example.ecommerce_social_media.ui.screens.auth.HomeScreen
import com.example.ecommerce_social_media.ui.screens.auth.ManagePostScreen
import com.example.ecommerce_social_media.ui.screens.auth.PostDetailScreen
import com.example.ecommerce_social_media.ui.screens.auth.PostScreen
import com.example.ecommerce_social_media.ui.screens.auth.ProfileScreen
import com.example.ecommerce_social_media.ui.screens.unauth.LoginScreen
import com.example.ecommerce_social_media.ui.screens.unauth.SignUpScreen


@Composable
fun AppNavigationGraph() {

    val postViewModel: PostViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        postViewModel.getCategories()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Crossfade(
            targetState = ESocialMediaAppRoute.currentScreen.value,
            label = ""
        ) { currentState ->
            when (currentState) {
                is Screen.LoginScreen -> {
                    LoginScreen()
                }

                is Screen.HomeScreen -> {
                    HomeScreen()
                }

                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.PostScreen -> {
                    PostScreen()
                }

                is Screen.NewsFeedScreen -> {
                    NewFeedScreen()
                }

                is Screen.UserProfileScreen -> {
                    ProfileScreen()
                }

                is Screen.ManagePost -> {
                    ManagePostScreen()
                }

                is Screen.GetPostsByCategoryScreen -> {
                    GetPostByCategoryScreen(categoryId = currentState.categoryId)
                }

                is Screen.PostDetailScreen -> {
                    PostDetailScreen(postId = currentState.postId)
                }

                is Screen.AdminHomeScreen -> {
                    AdminHomeScreen()
                }

                is Screen.UserManagementScreen -> {
                    UserManagementScreen()
                }

                is Screen.PostManagementScreen -> {
                    PostManagementScreen()
                }

                is Screen.CreatePollScreen -> {
                    CreatePollScreen()
                }

                is Screen.StatisticScreen -> {
                    StatisticScreen()
                }
            }
        }
    }
}