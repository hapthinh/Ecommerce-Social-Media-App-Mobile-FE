package com.example.ecommerce_social_media.ui.screens.unauth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.ui.components.MyTextFieldComponent
import com.example.ecommerce_social_media.ui.components.PasswordFieldComponent
import com.example.ecommerce_social_media.ui.events.SignUpUIEvent
import com.example.ecommerce_social_media.ui.auth.Viewmodel.SignUpViewModel


@Composable
fun SignUpScreen(
    SignUpViewModel: SignUpViewModel = hiltViewModel()
){
    Column (
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(android.graphics.Color.parseColor("#f8eeec")))
    ){
        Image(
            painter = painterResource(id = R.drawable.top_background2),
            contentDescription = null
        )
        Text(text = "Create\nAccount", color = Color(android.graphics.Color.parseColor("#Ea6d35"))
            , modifier = Modifier.padding(top = 16.dp, start = 24.dp)
            , fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold
        )
        MyTextFieldComponent(
            labelValue = stringResource(id = R.string.user),
            painterResource = painterResource(id = R.drawable.avatar),
            onTextSelected = {
                SignUpViewModel.onEvent(SignUpUIEvent.UserNameChanged(it))
            },
            errorStatus = SignUpViewModel.signupUIState.value.usernameError
        )

        PasswordFieldComponent(
            labelValue = stringResource(id = R.string.password),
            painterResource = painterResource(id = R.drawable.lock),
            onTextSelected = {
                SignUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(it))
            } ,
            errorStatus = SignUpViewModel.signupUIState.value.passwordError

        )

        PasswordFieldComponent(
            labelValue = stringResource(id = R.string.password),
            painterResource = painterResource(id = R.drawable.lock),
            onTextSelected = {
                SignUpViewModel.onEvent(SignUpUIEvent.ConfirmPasswordChanged(it))
            } ,
            errorStatus = SignUpViewModel.signupUIState.value.confirmpasswordError

        )

        Image(painter = painterResource(id = R.drawable.btn_arraw1),
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .padding(top = 24.dp)
                .align(Alignment.End)
                .clickable { SignUpViewModel.onEvent(SignUpUIEvent.SignUpButtonClicked) }
                .padding(end = 24.dp),
        )
    }
}


@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview(){
    SignUpScreen()
}