package com.example.ecommerce_social_media.ui.validation

import android.util.Patterns
import java.util.Date

object Validation {
    fun validateUsername(username: String): ValidationResult {
        return if (username.isNotEmpty()) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Username cannot be empty")
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty() && isEmailValid(email))
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return if (password.length >= 6) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Password must be at least 6 characters long")
        }
    }

    fun validateConfirmPassword(password: String, confirmpassword : String) : ValidationResult {
        return if(password == confirmpassword) {
            ValidationResult(status = true)
        } else {
            ValidationResult(status = false, errorMessage = "Password doesn't match")
        }
    }

    fun validateDate(date: Date): ValidationResult {
        return ValidationResult(
            isDateValid(date)
        )
    }


    fun isNumber(value: String): Boolean {
        return Patterns.PHONE.matcher(value).matches() }
}

fun isDateValid(date: Date): Boolean {
    val currentDate = Date()
    return !date.after(currentDate)
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } && password.any { it.isLetter() }
}

data class ValidationResult(
    val status: Boolean,
    val errorMessage: String? = null
)