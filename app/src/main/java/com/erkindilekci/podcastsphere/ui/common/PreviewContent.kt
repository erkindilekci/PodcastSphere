package com.erkindilekci.podcastsphere.ui.common

import androidx.compose.runtime.Composable
import com.erkindilekci.podcastsphere.ui.theme.PodcastSphereTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun PreviewContent(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    PodcastSphereTheme(darkTheme = darkTheme) {
        ProvideWindowInsets {
            content()
        }
    }
}