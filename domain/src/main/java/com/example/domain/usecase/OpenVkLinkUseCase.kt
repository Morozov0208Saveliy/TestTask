package com.example.domain.usecase

import android.content.Context
import com.example.domain.repository.ExternalLinkRepository

class OpenVkLinkUseCase(private val externalLinkRepository: ExternalLinkRepository) {
    fun execute(context: Context) {
        externalLinkRepository.openLink(context, "https://vk.com/")
    }
}