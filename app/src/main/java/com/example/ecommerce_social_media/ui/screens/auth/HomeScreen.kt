package com.example.ecommerce_social_media.ui.screens.auth

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.AccountResponse2
import com.example.ecommerce_social_media.data.entity.CategoryResponse
import com.example.ecommerce_social_media.data.entity.UserResponse
import com.example.ecommerce_social_media.ui.auth.Viewmodel.PostViewModel
import com.example.ecommerce_social_media.ui.components.AdsSlider
import com.example.ecommerce_social_media.ui.components.ColumnItemComponent
import com.example.ecommerce_social_media.ui.components.HeaderTextComponent
import com.example.ecommerce_social_media.ui.components.MyBottomBar
import com.example.ecommerce_social_media.ui.auth.Viewmodel.UserViewModel
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@Composable
fun HomeScreen(postViewModel: PostViewModel = hiltViewModel()) {

    val currentAccount by postViewModel.currentAccount.observeAsState()
    val categories by postViewModel.categories.observeAsState()

    var selectedCategoryId by remember {
        mutableStateOf<Int?>(null)
    }

    fun updateSelectedCategoryId(id: Int?) {
        selectedCategoryId = id
    }

    LaunchedEffect(Unit) {
        postViewModel.getCurrentAccount()
        postViewModel.getCategories()
    }

    LaunchedEffect(selectedCategoryId) {
        selectedCategoryId?.let { categoryId ->
            postViewModel.getPostByCategory(categoryId)
            ESocialMediaAppRoute.navigateTo(Screen.GetPostsByCategoryScreen(categoryId))
        }
    }

    Scaffold(
        bottomBar = {
            MyBottomBar()
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BodyContent(
                currentAccount = currentAccount,
                categories = categories,
                updateSelectedCategoryId = ::updateSelectedCategoryId
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyContent(
    currentAccount: AccountResponse2?,
    categories: List<CategoryResponse>?,
    updateSelectedCategoryId: (Int?) -> Unit
) {

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
                    Text(text = "HELLO", color = Color.White, fontSize = 18.sp)
                    val username = currentAccount?.user?.username ?: "Guest"

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
        var text by rememberSaveable {
            mutableStateOf("")
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Searching for...") },
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(43.dp)
                        .padding(end = 6.dp)
                )
            },
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, end = 24.dp, start = 24.dp)
                .shadow(3.dp, shape = RoundedCornerShape(50.dp))
                .background(Color.White, CircleShape)
        )
        HeaderTextComponent(value = "Khám phá danh mục")
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            categories?.forEach { category ->
                item {
                    ColumnItemComponent(
                        modifier = Modifier.padding(8.dp),
                        painterResource = painterResource(id = R.drawable.ic_bds),
                        value = category.category_name,
                        onItemClick = { updateSelectedCategoryId(category.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}