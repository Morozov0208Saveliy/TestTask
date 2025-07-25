package com.example.testtask.viewmodel

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.example.data.repository.impl.ExternalLinkRepositoryImpl
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.OpenOkLinkUseCase
import com.example.domain.usecase.OpenVkLinkUseCase

/**
 * Не знает про UI, просто отдает ему данные.
 */
class EntryViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val loginUseCase = LoginUseCase()

    // Подключаем репозитории для открытия ссылок.
    private val externalLinkRepository = ExternalLinkRepositoryImpl()
    private val openVkLinkUseCase = OpenVkLinkUseCase(externalLinkRepository)
    private val openOkLinkUseCase = OpenOkLinkUseCase(externalLinkRepository)

    // MediatorLiveData "наблюдает" за email и password. Как только хоть один из них меняется,
    // MediatorLiveData вызывает updateLoginButtonState() и решает, активна ли кнопка входа.
    val isLoginButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(email) { updateLoginButtonState() }
        addSource(password) { updateLoginButtonState() }
    }

    init {
        updateLoginButtonState()
    }

    /**
     * решает, активна ли кнопка входа.
     * Просит LoginUseCase проверить email и пароль и обновляет isLoginButtonEnabled.
     */
    private fun updateLoginButtonState() {
        val emailValue = email.value
        val passwordValue = password.value

        val buttonShouldBeEnabled = loginUseCase.validateCredentials(emailValue, passwordValue)
        isLoginButtonEnabled.value = buttonShouldBeEnabled // Отправляем новое состояние кнопки в UI
        Log.d("EntryViewModel", "Login Button Enabled State: $buttonShouldBeEnabled")
    }

    // Context тут нужен, потому что без него нельзя открыть внешнюю ссылку.
    fun openVkLink(context: Context) {
        openVkLinkUseCase.execute(context)
    }

    fun openOkLink(context: Context) {
        openOkLinkUseCase.execute(context)
    }


    // Для кнопок "Регистрация" и "Забыл пароль" всегда неактивны, как и было запрошено
    val isRegistrationButtonEnabled = MutableLiveData(false)
    val isForgotPasswordButtonEnabled = MutableLiveData(false)
}