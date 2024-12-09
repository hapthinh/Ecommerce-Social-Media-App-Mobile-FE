package com.example.ecommerce_social_media.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.CommentResponse
import com.example.ecommerce_social_media.data.entity.PostResponse
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    postId : Int,
    postViewModel: PostViewModel = hiltViewModel()
) {
    var commentText by remember { mutableStateOf("") }

    val post by postViewModel.posts3.collectAsState(initial = null)

    LaunchedEffect(postId) {
        postViewModel.getPostById(postId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        ESocialMediaAppRoute.navigateTo(Screen.HomeScreen)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35"))
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Hiển thị nội dung bài đăng
            post?.let { nonNullPost ->
                PostItemView2(
                    post = post!!,
                    currentUserId = post!!.account.user.id,
                    onPostClick = { postViewModel.selectPostForReaction(post!!.id) },
                    onLikeClick = { postId, isLiked ->
                        postViewModel.toggleReaction(postId, if (isLiked) 1 else 0)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(post!!.comment) { comment ->
                        CommentItemView(comment)
                    }
                }
            } ?: run {
                // Xử lý trường hợp bài viết không tồn tại
                Log.d("PostDetailScreen", "Bài viết không tồn tại")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    placeholder = { Text("Thêm bình luận") },
                    modifier = Modifier.weight(1f)
                )
                Log.d("PostDetail","value post $post")
                IconButton(onClick = {
                    val currentPost = post // Lưu giá trị của post vào biến cục bộ
                    Log.d("PostDetail","value $currentPost")
                    if (commentText.isNotEmpty() ) {
                        postViewModel.addComment(postId, commentText)
                        Log.d("PostDetail","value $commentText so 2")
                        commentText = ""
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = "Send Comment"
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItemView(comment: CommentResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = comment.account.user.username, style = MaterialTheme.typography.titleMedium)
            Text(text = comment.comment_content, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun PostItemView2(
    post: PostResponse,
    currentUserId: Int,
    onPostClick: () -> Unit,
    onLikeClick: (Int, Boolean) -> Unit
) {
    val likedByCurrentUser = remember { mutableStateOf(post.reaction.any { it.account.user.id == currentUserId && it.reaction.id == 1 }) }
    var updatedReactionCount by remember { mutableStateOf(post.reaction_count) } // Thêm biến để theo dõi reaction_count

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
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    IconButton(onClick = {
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
                Log.d("Postdetail","value post ${post}")
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    IconButton(onClick = {  }) {
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

