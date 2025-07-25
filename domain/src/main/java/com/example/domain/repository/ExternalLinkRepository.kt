package com.example.domain.repository

import android.content.Context

interface ExternalLinkRepository {
    fun openLink(context: Context, url: String)
}