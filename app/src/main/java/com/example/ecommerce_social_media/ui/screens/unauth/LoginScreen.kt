package com.example.ecommerce_social_media.ui.screens.unauth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.ui.auth.Viewmodel.LoginViewModel
import com.example.ecommerce_social_media.ui.components.ButtonComponent
import com.example.ecommerce_social_media.ui.components.ClickableLoginTextComponent
import com.example.ecommerce_social_media.ui.components.MyTextFieldComponent
import com.example.ecommerce_social_media.ui.components.PasswordFieldComponent
import com.example.ecommerce_social_media.ui.events.LoginUIEvent
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@Composable
fun LoginScreen (
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    Column (
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
    ){
        Image(
            painter = painterResource(id = R.drawable.top_background1),
            contentDescription = null
        )
        Text(text = "Welcome\nBack", color = Color(android.graphics.Color.parseColor("#Ea6d35"))
            , modifier = Modifier.padding(top = 16.dp, start = 24.dp)
            , fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold
        )
        MyTextFieldComponent(
            labelValue = stringResource(id = R.string.user),
            painterResource = painterResource(id = R.drawable.avatar),
            onTextSelected = {
                loginViewModel.onEvent(LoginUIEvent.UsernameChanged(it))
            },
            errorStatus = loginViewModel.loginUIState.value.usernameError
        )

        PasswordFieldComponent(
            labelValue = stringResource(id = R.string.password),
            painterResource = painterResource(id = R.drawable.lock),
            onTextSelected = {
                loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
            } ,
            errorStatus = loginViewModel.loginUIState.value.passwordError
        )

        Image(painter = painterResource(id = R.drawable.btn_arraw1),
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .padding(top = 24.dp)
                .align(Alignment.End)
                .clickable { loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked) }
                .padding(end = 24.dp),
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp)
        ){
            ButtonComponent(value = stringResource(id = R.string.google),
                painterResource = painterResource(
                    id = R.drawable.google
                ) , onButtonClicked = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .height(55.dp)
                    .weight(0.5f),
                modifier1 = Modifier
                    .padding(start = 14.dp)
                    .weight(0.7f),
                modifier2 = Modifier
                    .fillMaxWidth()
                    .weight(0.3f))

            ButtonComponent(
                value = stringResource(id = R.string.facebook),
                painterResource = painterResource(id = R.drawable.facebook),
                onButtonClicked = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 8.dp)
                    .height(55.dp)
                    .weight(0.5f),
                modifier1 = Modifier
                    .padding(start = 14.dp)
                    .weight(0.7f),
                modifier2 = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),)
        }
        ClickableLoginTextComponent (tryingToLogin = false, onTextSelected = {
            ESocialMediaAppRoute.navigateTo(Screen.SignUpScreen)
        })
    }
}
