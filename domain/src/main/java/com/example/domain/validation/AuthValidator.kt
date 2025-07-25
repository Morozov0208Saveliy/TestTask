package com.example.domain.validation

import java.util.regex.Pattern

object AuthValidator {

    private val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"

    fun isValidEmail(email: String?): Boolean {
        // Проверяем, что email не null и соответствует регулярному выражению
        return email != null && Pattern.matches(EMAIL_REGEX, email)
    }

    fun isValidPassword(password: String?): Boolean {
        // Проверяем, что пароль не null и не пустой
        return !password.isNullOrEmpty()
    }
}