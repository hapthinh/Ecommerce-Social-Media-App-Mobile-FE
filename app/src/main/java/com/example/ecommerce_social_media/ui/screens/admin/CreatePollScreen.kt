package com.example.ecommerce_social_media.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.PollRequestDTO
import com.example.ecommerce_social_media.data.entity.PostRequestDTO
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PollViewModel
import com.example.ecommerce_social_media.ui.components.TopBarHeaderText
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePollScreen(viewModel: PollViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var postContent by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val errorColor = Color(android.graphics.Color.parseColor("#Ea6d35"))
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "Tạo Khảo Sát") },
                navigationIcon = {
                    IconButton(onClick = { ESocialMediaAppRoute.navigateTo(Screen.AdminHomeScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Quay lại"
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
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // TextField để nhập tiêu đề khảo sát
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TextField để nhập thời gian bắt đầu
            TextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Thời gian bắt đầu") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TextField để nhập thời gian kết thúc
            TextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("Thời gian kết thúc") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TextField để nhập nội dung bài post
            TextField(
                value = postContent,
                onValueChange = { postContent = it },
                label = { Text("Nội dung bài post") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val postRequest = PostRequestDTO(post_content = postContent)

                    // Tạo Post trước
                    viewModel.createPost(postRequest, { createdPost ->
                        val pollRequest = PollRequestDTO(
                            title = title,
                            start_time = startTime,
                            end_time = endTime,
                            postId = createdPost.id
                        )

                        // Tạo Poll sau khi có Post
                        viewModel.createPoll(pollRequest, {
                            // Thông báo tạo thành công
                            errorMessage = "Tạo khảo sát thành công"
                        }, { error ->
                            errorMessage = error ?: "Lỗi tạo khảo sát"
                        })

                    }, { postError ->
                        errorMessage = postError ?: "Lỗi tạo bài post"
                    })
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = errorColor)

            ) {
                Text("Tạo Khảo Sát")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hiển thị thông báo lỗi
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
