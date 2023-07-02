package com.erkindilekci.podcastsphere.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.erkindilekci.podcastsphere.ui.common.StaggeredVerticalGrid

@Composable
fun LoadingPlaceholder() {
    StaggeredVerticalGrid(
        crossAxisCount = 2,
        spacing = 16.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        (1..10).map {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f))
                )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f))
                )
            }
        }
    }
}