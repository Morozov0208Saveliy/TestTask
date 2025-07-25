package com.example.testtask.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testtask.R
import com.example.testtask.viewmodel.EntryViewModel
import android.util.Log

/**
 * UI и слушает.
 * Сам логику не решает, просто передает данные в ViewModel и ждет от нее команды, что показать.
 */
class EntryFragment : Fragment() {

    private lateinit var viewModel: EntryViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registrationTextView: AppCompatTextView
    private lateinit var forgotPasswordTextView: AppCompatTextView
    private lateinit var vkButton: ImageButton
    private lateinit var odButton: ImageButton

    // Раздуваем фрагмент
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    // как только создан, подписываемся на изменения
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(EntryViewModel::class.java) // Получаем ViewModel.

        emailEditText = view.findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = view.findViewById(R.id.editTextTextPassword)
        loginButton = view.findViewById(R.id.button)
        registrationTextView = view.findViewById(R.id.textView2)
        forgotPasswordTextView = view.findViewById(R.id.textView3)
        vkButton = view.findViewById(R.id.button_vk)
        odButton = view.findViewById(R.id.button_od)


        setupInputFilters() // Настраиваем, что можно вводить в поля.
        setupTextWatchers() // Следим за тем, что пользователь печатает.
        setupButtonListeners() // Что происходит, когда пользователь нажимает кнопки.
        observeViewModel() // Смотрим за ViewModel, чтобы обновить экран.
    }

    /**
     * setupInputFilters: Запрещаем вводить всякую ерунду (например, кириллицу в email).
     * Используем Regex для маски ввода.
     */
    private fun setupInputFilters() {
        val emailFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val regex = Regex("[a-zA-Z0-9._%+-@]+")
            if (source.matches(regex)) {
                null// Разрешаем ввод
            } else {
                "" // Запрещаем ввод
            }
        }
        emailEditText.filters = arrayOf(emailFilter)
    }

    /**
     * setupTextWatchers: Реагируем на каждое изменение текста в полях.
     * Как только что-то меняется, сразу отправляем это в ViewModel.
     * (Email еще и чистим от пробелов и делаем маленькими буквами).
     */
    private fun setupTextWatchers() {
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredText = s.toString().toLowerCase().replace(" ", "")
                if (emailEditText.text.toString() != filteredText) {
                    emailEditText.setText(filteredText)
                    emailEditText.setSelection(filteredText.length)
                }
                viewModel.email.value = filteredText
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.password.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * setupButtonListeners: При нажатии на кнопку входа - переходим на главный экран.
     * При нажатии на VK/Одноклассники - просим ViewModel открыть ссылки.
     * Кнопки "Регистрация" и "Забыл пароль" пока ничего не делают.
     */
    private fun setupButtonListeners() {
        loginButton.setOnClickListener {
            // ИСПРАВЛЕНО: Переходим на MainScreenWrapperFragment, который содержит BottomNavigationView
            findNavController().navigate(R.id.action_entry_to_mainScreenWrapperFragment)
        }

        vkButton.setOnClickListener {
            context?.let {
                viewModel.openVkLink(it)
            }
        }

        odButton.setOnClickListener {
            context?.let {
                viewModel.openOkLink(it)
            }
        }


        registrationTextView.setOnClickListener { /* Do nothing */ }
        forgotPasswordTextView.setOnClickListener { /* Do nothing */ }
    }

    /**
     * Мы "подписываемся" на изменения в ViewModel (например, когда кнопка входа должна быть активной).
     * Когда ViewModel что-то меняет, мы тут же обновляем наш UI.
     */
    private fun observeViewModel() {
        viewModel.isLoginButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            Log.d("EntryFragment", "Login Button isEnabled: $isEnabled")
            loginButton.isEnabled = isEnabled // Включаем/выключаем кнопку
            loginButton.alpha = if (isEnabled) 1.0f else 0.5f
        }

        // Подписки для неактивных кнопок.
        viewModel.isRegistrationButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            registrationTextView.isEnabled = isEnabled
            registrationTextView.alpha = if (isEnabled) 1.0f else 0.5f
        }


        viewModel.isForgotPasswordButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            forgotPasswordTextView.isEnabled = isEnabled
            forgotPasswordTextView.alpha = if (isEnabled) 1.0f else 0.5f
        }
    }
}