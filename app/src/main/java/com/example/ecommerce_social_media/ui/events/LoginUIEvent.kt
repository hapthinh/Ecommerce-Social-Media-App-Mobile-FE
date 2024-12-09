package com.example.ecommerce_social_media.ui.events

sealed class LoginUIEvent {
    data class UsernameChanged(val username: String) : LoginUIEvent()
    data class PasswordChanged(val password: String) : LoginUIEvent()

    object LoginButtonClicked : LoginUIEvent()
}

data class LoginUIState (
    val username: String = "",
    val password: String = "",

    val usernameError: Boolean = false,
    val passwordError: Boolean = false
)