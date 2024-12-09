package com.example.ecommerce_social_media.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.PostResponse
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.components.TopBarHeaderText
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagePostScreen(postViewModel: PostViewModel = hiltViewModel()) {
    val posts by postViewModel.posts.observeAsState(initial = emptyList())
    val currentAccount by postViewModel.currentAccount.observeAsState() // Thêm biến để quan sát tài khoản hiện tại

    LaunchedEffect(Unit) {
        postViewModel.getCurrentPost()
        postViewModel.getCurrentAccount() // Gọi API để lấy tài khoản hiện tại
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "Quản lý bài đăng") },
                navigationIcon = {
                    IconButton(onClick = { ESocialMediaAppRoute.navigateTo(Screen.HomeScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Quay về trang chủ"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(
                        android.graphics.Color.parseColor(
                            "#Ea6d35"
                        )
                    )
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (posts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Không có bài đăng nào.")
                }
            } else {
                LazyColumn {
                    items(posts) { post ->
                        PostItemView(post, currentAccount?.user?.id ?: 0)
                    }
                }
            }
        }
    }
}

@Composable
fun PostItemView(post: PostResponse, currentUserId: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .fillMaxHeight(),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(width = 3.dp, color = Color.Gray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .weight(0.3f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(0.7f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = post.account.user.username,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Text(
                        text = post.created_date.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Xử lý nút menu */ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.Gray
                    )
                }
            }

            Column {
                Text(
                    text = post.post_content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                post.product?.let {
                    Text(
                        text = "Sản phẩm: ${it.product_name}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(android.graphics.Color.parseColor("#EA6D35"))
                    )
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Giá: ${it.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(android.graphics.Color.parseColor("#3b608c"))
                    )
                }
            }

            Divider(
                color = Color.Gray,
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val likedByCurrentUser = post.reaction.any { it.account.user.id == currentUserId }
                Column(
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(
                                if (likedByCurrentUser) R.drawable.ic_like else R.drawable.ic_unlike
                            ),
                            contentDescription = "Like",
                        )
                    }
                    Text(
                        text = "${post.reaction_count}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }


                Spacer(modifier = Modifier.width(16.dp))

                Column(
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_comment),
                            contentDescription = "Comment",
                            tint = Color.Gray
                        )
                    }
                    Text(
                        text = "${post.comment_count}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            post.comment.forEach { comment ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = comment.account.user.username,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color(android.graphics.Color.parseColor("#3b608c"))
                        )
                        Text(
                            text = comment.comment_content,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(android.graphics.Color.parseColor("#3b608c"))
                        )
                    }
                }
            }
        }
    }
}


