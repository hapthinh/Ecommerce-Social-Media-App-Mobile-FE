package com.example.ecommerce_social_media.ui.newfeed

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
fun NewFeedScreen(
    postViewModel: PostViewModel = hiltViewModel()
) {
    val posts by postViewModel.posts.observeAsState(initial = emptyList())
    val currentAccount by postViewModel.currentAccount.observeAsState()

    LaunchedEffect(Unit) {
        postViewModel.getNewFeedPosts(1)
        postViewModel.getCurrentAccount()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "News Feed") },
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
        LazyColumn(
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding()
            ),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(posts) { post ->
                PostItemView2(post, currentAccount?.user?.id ?: 0,
                    onPostClick = {
                        postViewModel.selectPostForReaction(post.id)
                    },
                    onLikeClick = { postId, isLiked ->
                        postViewModel.selectPostForReaction(postId)
                        postViewModel.toggleReaction(postId, currentReactionId = if (isLiked) 1 else 0)
                    },
                    onEditPost = { updatedPost -> postViewModel.updatePost(updatedPost) },
                    onDeletePost = { postId -> postViewModel.deletePost(postId) }
                )
            }
        }
    }

}

@Composable
fun PostItemView2(
        post: PostResponse,
        currentUserId: Int,
        onPostClick: () -> Unit,
        onLikeClick: (Int, Boolean) -> Unit,
        onEditPost: (PostResponse) -> Unit,
        onDeletePost: (Int) -> Unit
) {
    val likedByCurrentUser = remember { mutableStateOf(post.reaction.any { it.account.user.id == currentUserId && it.reaction.id == 1 }) }
    var updatedReactionCount by remember { mutableStateOf(post.reaction_count) }

    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }


    fun showOptionsMenu() {
        showMenu = true
    }

    @Composable
    fun showEditDialog() {

        var showDialog by remember { mutableStateOf(true) }

        if (showDialog) {

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Chỉnh sửa bài đăng") },
                text = {
                    Column {
                        // TextField cho post_content
                        TextField(
                            value = post.post_content,
                            onValueChange = {  },
                            label = { Text("Nội dung") }
                        )
                        // Nếu có sản phẩm, thêm TextField cho product
                        post.product?.let {
                            TextField(
                                value = it.product_name,
                                onValueChange = {  },
                                label = { Text("Tên sản phẩm") }
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onEditPost(post)
                            showDialog = false
                        }
                    ) {
                        Text("Lưu")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Thoát")
                    }
                }
            )
        }
    }

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
                Column (
                    modifier = Modifier.weight(0.7f),
                    horizontalAlignment = Alignment.Start
                ){
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
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Lựa chọn",
                        tint = Color.Gray
                    )
                }
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Xác nhận xóa") },
                        text = { Text("Bạn có chắc muốn xóa không?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    onDeletePost(post.id)
                                    showDeleteDialog = false
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Có")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteDialog = false }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                                contentColor = Color.White
                            )) {
                                Text("Không")
                            }
                        }
                    )
                }

                if (showMenu) {
                    AlertDialog(
                        onDismissRequest = { showMenu = false },
                        title = { Text("Chọn tác vụ") },
                        text = {
                            Column {
                                Button(onClick = {
                                    onEditPost(post)
                                    showMenu = false
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                                    contentColor = Color.White
                                )) {
                                    Text("Sửa bài viết")
                                }
                                Button(onClick = {
                                    showDeleteDialog = true
                                    showMenu = false
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                                    contentColor = Color.White
                                )) {
                                    Text("Xóa bài viết")
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = { showMenu = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Đóng")
                            }
                        }
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
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    IconButton(onClick = {
                        Log.d("NewFeedScreen", likedByCurrentUser.value.toString())
                        likedByCurrentUser.value = !likedByCurrentUser.value
                        updatedReactionCount = if (likedByCurrentUser.value) {
                            onLikeClick(post.id, true)
                            updatedReactionCount + 1
                        } else {
                            onLikeClick(post.id, false)
                            updatedReactionCount - 1
                        }
                    },) {
                        Icon(
                            painter = painterResource(
                                if (likedByCurrentUser.value) R.drawable.ic_like else R.drawable.ic_unlike
                            ),
                            contentDescription = "Like",
                        )
                    }
                    Text(
                        text = "$updatedReactionCount",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }


                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    IconButton(onClick = { ESocialMediaAppRoute.navigateTo(Screen.PostDetailScreen(post.id)) }) {
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
        }
    }
}

