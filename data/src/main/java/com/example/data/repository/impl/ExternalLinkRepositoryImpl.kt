package com.example.data.repository.impl

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.example.domain.repository.ExternalLinkRepository

class ExternalLinkRepositoryImpl : ExternalLinkRepository {
    override fun openLink(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}