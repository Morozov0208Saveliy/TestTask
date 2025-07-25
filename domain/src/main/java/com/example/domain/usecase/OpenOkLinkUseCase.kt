package com.example.domain.usecase

import android.content.Context
import com.example.domain.repository.ExternalLinkRepository

/**
 * Он знает только, что есть какой-то "репозиторий", который умеет открывать ссылки.
 * Use Case зависит от интерфейса, а не от конкретной реализации.
 */
class OpenOkLinkUseCase(private val externalLinkRepository: ExternalLinkRepository) {
    /**
     * execute: "Выполнить" - просто просим репозиторий открыть ссылку на VK.
     */
    fun execute(context: Context) {
        externalLinkRepository.openLink(context, "https://ok.ru/")
    }
}