package com.erkindilekci.podcastsphere.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.erkindilekci.podcastsphere.domain.model.Episode
import com.erkindilekci.podcastsphere.ui.podcast.PodcastImage

@Composable
fun PodcastView(
    podcast: Episode,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick),
    ) {
        PodcastImage(
            url = podcast.thumbnail,
            aspectRatio = 1f
        )
        Text(
            podcast.titleOriginal,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}
