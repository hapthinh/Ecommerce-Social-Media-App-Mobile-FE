package com.example.ecommerce_social_media.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.components.TopBarHeaderText
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(postViewModel: PostViewModel = hiltViewModel()){
    val currentAccount by postViewModel.currentAccount.observeAsState()

    LaunchedEffect(Unit) {
        postViewModel.getCurrentAccount()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "Thông tin cá nhân") },
                navigationIcon = {
                    IconButton(onClick = {ESocialMediaAppRoute.navigateTo(Screen.HomeScreen)}){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Quay về trang chủ"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.parseColor("#EA6D35")),
                                Color(android.graphics.Color.parseColor("#3b608c"))
                            )
                        ), shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "User Avatar",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentAccount?.user?.username ?: "None",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Thông tin cá nhân", color = Color.Blue, fontSize = 20.sp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProfileItem(iconRes = R.drawable.img_2, label = "Lịch sử đăng bài", icon2 = R.drawable.img_1, onClick = {ESocialMediaAppRoute.navigateTo(Screen.ManagePost)})
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = currentAccount?.user?.username ?: "",
                    onValueChange = {  },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { ESocialMediaAppRoute.navigateTo(Screen.LoginScreen) },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(android.graphics.Color.parseColor("#Ea6d35")),
                        contentColor = Color.White
                    ),
                ) {
                    Text("Đăng xuất")
                }
            }
        }
    }
}

@Composable
fun ProfileItem(iconRes: Int, label: String, icon2: Int, onClick: ()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp)).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Sắp xếp các phần tử trong Row
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }

        Image(
            painter = painterResource(id = icon2), // Hiển thị icon thứ hai
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}