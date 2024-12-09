package com.example.ecommerce_social_media.ui.events

sealed class SignUpUIEvent {
    data class UserNameChanged(val username: String) : SignUpUIEvent()
    data class PasswordChanged(val password: String) : SignUpUIEvent()
    data class ConfirmPasswordChanged(val confirmpassword: String) : SignUpUIEvent()

    object SignUpButtonClicked : SignUpUIEvent()
}

data class SignUpUIState (
    val username: String = "",
    val password: String = "",
    val confirmpassword: String = "",

    val usernameError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmpasswordError: Boolean = false
)