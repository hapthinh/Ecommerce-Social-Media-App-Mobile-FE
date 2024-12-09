package com.example.ecommerce_social_media.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.CategoryResponse
import com.example.ecommerce_social_media.data.entity.PostRequest2
import com.example.ecommerce_social_media.data.entity.ProductResponse // Sửa từ ProductResponse2 thành ProductResponse
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.components.TopBarHeaderText
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen() {
    val postViewModel: PostViewModel = hiltViewModel()
    val categories by postViewModel.categories.observeAsState(emptyList())
    val currentAccount by postViewModel.currentAccount4.observeAsState()

    var selectedCategory by remember { mutableStateOf<CategoryResponse?>(null) }
    var postContent by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        postViewModel.getCategories()
        postViewModel.getCurrentAccount()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "Đăng bài mới") },
                navigationIcon = {
                    IconButton(onClick = { ESocialMediaAppRoute.navigateTo(Screen.HomeScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Quay về trang chủ"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35"))
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(16.dp).padding(innerPadding)) {

            Spacer(modifier = Modifier.height(40.dp))

            // Display categories as a grid of radio buttons (2 per row)
            Text("Chọn danh mục", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            categories.chunked(2).forEach { rowCategories ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowCategories.forEach { category ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp)
                                .clickable { selectedCategory = category }
                        ) {
                            RadioButton(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(
                                text = category.category_name,
                                color = if (selectedCategory == category) Color(android.graphics.Color.parseColor("#Ea6d35")) else Color.Gray,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = postContent,
                onValueChange = { postContent = it },
                label = { Text("Nội dung bài viết") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Tên sản phẩm") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                label = { Text("Giá sản phẩm") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                label = { Text("Mô tả sản phẩm") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (selectedCategory != null && postContent.isNotBlank() && productName.isNotBlank() &&
                        productPrice.isNotBlank() && productDescription.isNotBlank()
                    ) {
                        val productResponse = ProductResponse(
                            id = 0,
                            product_name = productName,
                            description = productDescription,
                            price = productPrice,
                            category = selectedCategory!!
                        )
                        currentAccount?.let { account ->
                            val postRequest = PostRequest2(
                                post_content = postContent,
                                account = account,
                                product = productResponse
                            )

                            postViewModel.postNewPost(postRequest)
                        } ?: run {
                            Log.d("PostScreen", "Không có tài khoản hiện tại")
                        }
                    } else {
                        Log.d("PostScreen", "Vui lòng điền đầy đủ thông tin")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                    contentColor = Color.White
                ),
            ) {
                Text(text = "Đăng bài")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    PostScreen()
}
