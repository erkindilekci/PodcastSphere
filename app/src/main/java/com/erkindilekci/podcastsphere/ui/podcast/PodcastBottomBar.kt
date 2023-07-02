package com.erkindilekci.podcastsphere.ui.podcast

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erkindilekci.podcastsphere.R
import com.erkindilekci.podcastsphere.domain.model.Episode
import com.erkindilekci.podcastsphere.domain.model.Podcast
import com.erkindilekci.podcastsphere.ui.common.PreviewContent
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastPlayerViewModel
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun PodcastBottomBar(
    modifier: Modifier = Modifier
) {
    val playerViewModel: PodcastPlayerViewModel = hiltViewModel()
    val episode = playerViewModel.currentPlayingEpisode.value

    AnimatedVisibility(
        visible = episode != null,
        modifier = modifier
    ) {
        if (episode != null) {
            PodcastBottomBarContent(episode)
        }
    }
}

@Composable
fun PodcastBottomBarContent(episode: Episode) {
    val playerViewModel: PodcastPlayerViewModel = hiltViewModel()

    val endAnchor = LocalConfiguration.current.screenWidthDp * LocalDensity.current.density
    val anchors = mapOf(
        0f to 0,
        endAnchor to 1
    )

    val iconResId =
        if (playerViewModel.podcastIsPlaying) R.drawable.ic_round_pause else R.drawable.ic_round_play_arrow

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.54f) },
//                orientation = Orientation.Horizontal
//            )
    ) {
//        if (swipeableState.currentValue >= 1) {
//            LaunchedEffect("key") {
//                podcastPlayer.stopPlayback()
//            }
//        }

        PodcastBottomBarStatelessContent(
            episode = episode,
            xOffset = 0,
            darkTheme = isSystemInDarkTheme(),
            icon = iconResId,
            onTooglePlaybackState = {
                playerViewModel.tooglePlaybackState()
            }
        ) {
            playerViewModel.showPlayerFullScreen = true
        }
    }
}

@Composable
fun PodcastBottomBarStatelessContent(
    episode: Episode,
    xOffset: Int,
    darkTheme: Boolean,
    @DrawableRes icon: Int,
    onTooglePlaybackState: () -> Unit,
    onTap: (Offset) -> Unit,
) {
    Box(
        modifier = Modifier
            .offset { IntOffset(xOffset, 0) }
            .background(if (darkTheme) Color(0xFF343434) else Color(0xFFF1F1F1))
            .navigationBarsPadding()
            .height(64.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = onTap
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = episode.thumbnail,
                contentDescription = stringResource(R.string.podcast_thumbnail),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp),
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
            ) {
                Text(
                    episode.titleOriginal,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    episode.podcast.titleOriginal,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.graphicsLayer {
                        alpha = 0.60f
                    }
                )
            }

            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(R.string.play),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onTooglePlaybackState)
                    .padding(6.dp)
            )
        }
    }
}

@Preview(name = "Bottom Bar")
@Composable
fun PodcastBottomBarPreview() {
    PreviewContent(darkTheme = true) {
        PodcastBottomBarStatelessContent(
            episode = Episode(
                "1",
                "",
                "",
                "https://picsum.photos/200",
                Podcast("", "", "", "This is podcast title", "", "This is publisher"),
                "https://picsum.photos/200",
                0,
                "This is a title",
                "",
                2700,
                false,
                "This is a description"
            ),
            xOffset = 0,
            darkTheme = true,
            icon = R.drawable.ic_round_play_arrow,
            onTooglePlaybackState = { },
            onTap = { }
        )
    }
}

