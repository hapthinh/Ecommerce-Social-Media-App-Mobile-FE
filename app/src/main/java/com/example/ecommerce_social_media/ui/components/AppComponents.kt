package com.example.ecommerce_social_media.ui.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.ui.theme.Primary
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ManageHistory
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.PostAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean
){
    val textValue = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .background(Color.White, CircleShape),
        label = { Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = Primary
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Image(painter = painterResource, contentDescription = "", modifier = Modifier
                .size(43.dp)
                .padding(start = 6.dp)
                .padding(3.dp))
        },
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdsSlider(
    modifier: Modifier = Modifier
        .height(150.dp)
        .padding(top = 12.dp, bottom = 12.dp, end = 12.dp)
        .background(
            color = Color(android.graphics.Color.parseColor("#ffe0c8")),
            shape = RoundedCornerShape(20.dp)
        )
) {
    val pagerItems = listOf(
        PagerItem(R.drawable.a1),
        PagerItem(R.drawable.a2),
        PagerItem(R.drawable.a3)
    )

    val pagerState = rememberPagerState(
        pageCount = { pagerItems.size }
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPagerSection(pagerItems, pagerState)
        PageIndicator(
            pageCount = pagerItems.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}

@Composable
fun ColumnItemComponent(modifier: Modifier, painterResource: Painter,value: String, onItemClick: () -> Unit){
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource,
            contentDescription = null,
            Modifier
                .padding(top = 8.dp, bottom = 2.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
                .clickable { onItemClick() }
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
            color = Color(android.graphics.Color.parseColor("#2e3d6d"))
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerSection(pagerItems: List<PagerItem>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) { currentPage ->
        val pagerItem = pagerItems[currentPage]

        Image(
            painter = painterResource(id = pagerItem.imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )
    }
}

data class PagerItem(
    val imageResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldComponent(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .background(Color.White, CircleShape),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = Primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Image(painter = painterResource, contentDescription = "", modifier = Modifier
                .size(43.dp)
                .padding(start = 6.dp)
                .padding(3.dp))
        },
        trailingIcon = {
            val image = if(passwordVisible.value){
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible.value) "Hide password" else "Show Password"

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Composable
fun ButtonComponent(value: String, modifier: Modifier = Modifier, painterResource: Painter, onButtonClicked: () -> Unit, isEnabled: Boolean = false,
                    modifier1: Modifier = Modifier,
                    modifier2: Modifier = Modifier){
    Button(
        modifier = modifier,
        border = BorderStroke(1.dp,Color(android.graphics.Color.parseColor("#4d4d4d")),
        ),
        onClick = {
            onButtonClicked.invoke()
        },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        Column (
            modifier = modifier2,
            verticalArrangement = Arrangement.Center
        ){
            Image(painter = painterResource, contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .clickable { })
        }
        Column (
            modifier = modifier1,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = value,
                color = Color(android.graphics.Color.parseColor("#2f4f86")),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Bạn đã có tài khoản? " else "Bạn chưa có tài khoản? "
    val loginText = if (tryingToLogin) "Đăng Nhập" else "Đăng Ký"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }
        },
    )
}

@Composable
fun MyBottomBar(){
    val items = listOf(
        BottomBarItem.DashBoard,
        BottomBarItem.ManagePost,
        BottomBarItem.Post,
        BottomBarItem.NewsFeed,
        BottomBarItem.Profile
    )

    NavigationBar (
        Modifier.height(80.dp),
        containerColor = Color.White,
    ) {
        items.forEach { screenItem ->
            NavigationBarItem(
                selected = ESocialMediaAppRoute.currentScreen.value == screenItem.screen,
                onClick = { ESocialMediaAppRoute.currentScreen.value = screenItem.screen },
                icon = {
                    Icon(screenItem.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp)
                    ) },
                label = {
                    Text(screenItem.label,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

sealed class BottomBarItem(val screen: Screen, val icon : ImageVector, val label:String){
    object DashBoard : BottomBarItem(Screen.HomeScreen, Icons.Rounded.Home, "Home")
    object NewsFeed : BottomBarItem(Screen.NewsFeedScreen, Icons.Rounded.Newspaper, "News Feed")
    object ManagePost : BottomBarItem(Screen.ManagePost, Icons.Rounded.ManageHistory, "Manage Post")
    object Profile : BottomBarItem(Screen.UserProfileScreen, Icons.Rounded.AccountCircle, "Profile")
    object Post : BottomBarItem(Screen.PostScreen, Icons.Rounded.PostAdd, "Post")
}

@Composable
fun HeaderTextComponent(value: String){
    val text = "See all"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = text, annotation = text)
            append(text)
        }
    }
    Row (modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)){
        Text(
            text = value,
            color = Color.Black,
            fontSize = 23.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        ClickableText(
            text = annotatedString,
            onClick = {
                ESocialMediaAppRoute.navigateTo(Screen.NewsFeedScreen)
            },
        )
    }
}

@Composable
fun TopBarHeaderText(value: String){
    Text(
        text = value,
        color = Color.White,
        fontSize = 23.sp,

    )
}