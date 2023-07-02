package com.erkindilekci.podcastsphere.ui.common

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle

@Composable
fun EmphasisText(
    text: String,
    contentAlpha: Float = 0.38f,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = 0.4f)) {

    }
}