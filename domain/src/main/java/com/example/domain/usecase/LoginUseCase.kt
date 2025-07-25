package com.example.domain.usecase

import com.example.domain.validation.AuthValidator

/**
 * LoginUseCase: Это как "мозг" для входа.
 * Просто проверяем, правильные ли email и пароль по нашим правилам.
 */
class LoginUseCase {

    /**
     * validateCredentials: Сама проверка.
     * Просим AuthValidator проверить email и пароль.
     */
    fun validateCredentials(email: String?, password: String?): Boolean {
        val isEmailValid = AuthValidator.isValidEmail(email)
        val isPasswordValid = AuthValidator.isValidPassword(password)
        return isEmailValid && isPasswordValid
    }
}