package com.erkindilekci.podcastsphere.ui.podcast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.erkindilekci.podcastsphere.R

@Composable
fun PodcastImage(
    url: String,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f,
) {

    Box(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .aspectRatio(aspectRatio)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f))
    ) {
        AsyncImage(
            model = url,
            contentDescription = stringResource(R.string.podcast_thumbnail),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}