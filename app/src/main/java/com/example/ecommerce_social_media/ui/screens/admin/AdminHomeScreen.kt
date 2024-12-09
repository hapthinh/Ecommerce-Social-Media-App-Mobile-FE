package com.example.ecommerce_social_media.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.AccountResponse2
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.components.AdsSlider
import com.example.ecommerce_social_media.ui.components.ColumnItemComponent
import com.example.ecommerce_social_media.ui.components.HeaderTextComponent
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@Composable
fun AdminHomeScreen(postViewModel: PostViewModel = hiltViewModel()) {

    val currentAccount by postViewModel.currentAccount.observeAsState()

    LaunchedEffect(Unit) {
        postViewModel.getCurrentAccount()
    }

    Scaffold(
        bottomBar = {
            AdminBottomBar()
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AdminBodyContent(currentAccount = currentAccount)
        }
    }

}

@Composable
fun AdminBodyContent(currentAccount: AccountResponse2?) {

    Column(
        Modifier
            .fillMaxWidth()
            .height(1100.dp)
            .background(color = Color(android.graphics.Color.parseColor("#f8eeec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout {
            val (topImg, profile) = createRefs()
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(245.dp)
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(android.graphics.Color.parseColor("#EA6D35")),
                                Color(android.graphics.Color.parseColor("#3b608c"))
                            )
                        ), shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp)
                    )
            )
            Row(
                modifier = Modifier
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(start = 14.dp)
                        .weight(0.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "HELLO ADMIN", color = Color.White, fontSize = 18.sp)
                    val username = currentAccount?.user?.username ?: "Admin"

                    Text(
                        text = username, color = Color.White, fontSize = 22.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 14.dp)
                    )
                }
                Image(painter = painterResource(id = R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable { }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .shadow(3.dp, shape = RoundedCornerShape(20.dp))
                    .constrainAs(profile) {
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                AdsSlider()
            }
        }

        HeaderTextComponent(value = "Admin Dashboard")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 30.dp, top = 16.dp)
        ) {
            item {
                ColumnItemComponent(
                    modifier = Modifier.padding(8.dp),
                    painterResource = painterResource(id = R.drawable.ic_bds),
                    value = "Quản lý người dùng",
                    onItemClick = {
                        ESocialMediaAppRoute.navigateTo(Screen.UserManagementScreen)
                    }
                )
            }
            item {
                ColumnItemComponent(
                    modifier = Modifier.padding(8.dp),
                    painterResource = painterResource(id = R.drawable.ic_bds),
                    value = "Tạo khảo sát",
                    onItemClick = {
                        ESocialMediaAppRoute.navigateTo(Screen.CreatePollScreen)
                    }
                )
            }
            item {
                ColumnItemComponent(
                    modifier = Modifier.padding(8.dp),
                    painterResource = painterResource(id = R.drawable.ic_bds),
                    value = "Quản lý bài đăng",
                    onItemClick = {
                        ESocialMediaAppRoute.navigateTo(Screen.PostManagementScreen)
                    }
                )
            }
            item {
                ColumnItemComponent(
                    modifier = Modifier.padding(8.dp),
                    painterResource = painterResource(id = R.drawable.ic_bds),
                    value = "Thống kê",
                    onItemClick = {
                        ESocialMediaAppRoute.navigateTo(Screen.StatisticScreen)
                    }
                )
            }
        }
    }
}

@Composable
fun AdminBottomBar() {
}
