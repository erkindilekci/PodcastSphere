package com.erkindilekci.podcastsphere.ui.podcast

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erkindilekci.podcastsphere.R
import com.erkindilekci.podcastsphere.domain.model.Episode
import com.erkindilekci.podcastsphere.domain.model.Podcast
import com.erkindilekci.podcastsphere.ui.common.EmphasisText
import com.erkindilekci.podcastsphere.ui.common.IconButton
import com.erkindilekci.podcastsphere.ui.common.PreviewContent
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastPlayerViewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastSearchViewModel
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun PodcastPlayerScreen(backDispatcher: OnBackPressedDispatcher) {
    val podcastPlayer = hiltViewModel<PodcastPlayerViewModel>()
    val episode = podcastPlayer.currentPlayingEpisode.value

    AnimatedVisibility(
        visible = episode != null && podcastPlayer.showPlayerFullScreen,
        enter = slideInVertically(
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            targetOffsetY = { it }
        )
    ) {
        if (episode != null) {
            PodcastPlayerBody(episode, backDispatcher)
        }
    }
}

@Composable
fun PodcastPlayerBody(episode: Episode, backDispatcher: OnBackPressedDispatcher) {
    val podcastPlayer = hiltViewModel<PodcastPlayerViewModel>()
//    val swipeableState = rememberSwipeableState(0)
    val endAnchor = LocalConfiguration.current.screenHeightDp * LocalDensity.current.density
    val anchors = mapOf(
        0f to 0,
        endAnchor to 1
    )

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                podcastPlayer.showPlayerFullScreen = false
            }
        }
    }

    val backgroundColor = MaterialTheme.colorScheme.background
    var gradientColor by remember {
        mutableStateOf(backgroundColor)
    }

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(episode.image)
        .target {
            podcastPlayer.calculateColorPalette(it) { color ->
                gradientColor = color
            }
        }
        .build()

    val imagePainter = ImageRequest.Builder(LocalContext.current)
        .data(imageRequest)
        .crossfade(true)
        .build()

    val iconResId =
        if (podcastPlayer.podcastIsPlaying) R.drawable.ic_round_pause else R.drawable.ic_round_play_arrow

    var sliderIsChanging by remember { mutableStateOf(false) }

    var localSliderValue by remember { mutableStateOf(0f) }

    val sliderProgress =
        if (sliderIsChanging) localSliderValue else podcastPlayer.currentEpisodeProgress

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.34f) },
//                orientation = Orientation.Vertical
//            )
    ) {
//        if (swipeableState.currentValue >= 1) {
        LaunchedEffect("key") {
            podcastPlayer.showPlayerFullScreen = false
        }
//        }

        PodcastPlayerSatelessContent(
            episode = episode,
            darkTheme = isSystemInDarkTheme(),
            imagePainter = imagePainter,
            gradientColor = gradientColor,
//            yOffset = swipeableState.offset.value.roundToInt(),
            yOffset = 0,
            playPauseIcon = iconResId,
            playbackProgress = sliderProgress,
            currentTime = podcastPlayer.currentPlaybackFormattedPosition,
            totalTime = podcastPlayer.currentEpisodeFormattedDuration,
            onRewind = {
                podcastPlayer.rewind()
            },
            onForward = {
                podcastPlayer.fastForward()
            },
            onTooglePlayback = {
                podcastPlayer.tooglePlaybackState()
            },
            onSliderChange = { newPosition ->
                localSliderValue = newPosition
                sliderIsChanging = true
            },
            onSliderChangeFinished = {
                podcastPlayer.seekToFraction(localSliderValue)
                sliderIsChanging = false
            }
        ) {
            podcastPlayer.showPlayerFullScreen = false
        }
    }

    LaunchedEffect("playbackPosition") {
        podcastPlayer.updateCurrentPlaybackPosition()
    }

    DisposableEffect(backDispatcher) {
        backDispatcher.addCallback(backCallback)

        onDispose {
            backCallback.remove()
            podcastPlayer.showPlayerFullScreen = false
        }
    }
}

@Composable
fun PodcastPlayerSatelessContent(
    episode: Episode,
    imagePainter: ImageRequest,
    gradientColor: Color,
    yOffset: Int,
    @DrawableRes playPauseIcon: Int,
    playbackProgress: Float,
    currentTime: String,
    totalTime: String,
    darkTheme: Boolean,
    onRewind: () -> Unit,
    onForward: () -> Unit,
    onTooglePlayback: () -> Unit,
    onSliderChange: (Float) -> Unit,
    onSliderChangeFinished: () -> Unit,
    onClose: () -> Unit
) {
    val gradientColors = if (darkTheme) {
        listOf(gradientColor, MaterialTheme.colorScheme.background)
    } else {
        listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.background)
    }

    val sliderColors = if (darkTheme) {
        SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.onBackground,
            activeTrackColor = MaterialTheme.colorScheme.onBackground,
            inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = ProgressIndicatorDefaults.circularColor.alpha
            ),
        )
    } else SliderDefaults.colors(
        thumbColor = gradientColor,
        activeTrackColor = gradientColor,
        inactiveTrackColor = gradientColor.copy(
            alpha = ProgressIndicatorDefaults.circularColor.alpha
        )
    )

    Box(
        modifier = Modifier
            .offset { IntOffset(0, yOffset) }
            .fillMaxSize()
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = gradientColors,
                            endY = LocalConfiguration.current.screenHeightDp.toFloat() * LocalDensity.current.density / 2
                        )
                    )
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                Column {
                    IconButton(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.close),
                        onClick = onClose
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(vertical = 32.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .weight(1f, fill = false)
                                .aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f))
                        ) {
                            AsyncImage(
                                model = imagePainter,
                                contentDescription = stringResource(R.string.podcast_thumbnail),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Text(
                            episode.titleOriginal,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Text(
                            episode.podcast.titleOriginal,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.graphicsLayer {
                                alpha = 0.60f
                            }
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            Slider(
                                value = playbackProgress,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = sliderColors,
                                onValueChange = onSliderChange,
                                onValueChangeFinished = onSliderChangeFinished,
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                EmphasisText(text = currentTime)
                                EmphasisText(text = totalTime)
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_round_replay_10),
                                contentDescription = stringResource(R.string.replay_10_seconds),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = onRewind)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                painter = painterResource(playPauseIcon),
                                contentDescription = stringResource(R.string.play),
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                                    .clickable(onClick = onTooglePlayback)
                                    .size(64.dp)
                                    .padding(8.dp)
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_round_forward_10),
                                contentDescription = stringResource(R.string.forward_10_seconds),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = onForward)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Player")
@Composable
fun PodcastPlayerPreview() {
    PreviewContent(darkTheme = true) {
        PodcastPlayerSatelessContent(
            episode = Episode(
                "1",
                "",
                "",
                "",
                Podcast("", "", "", "This is podcast title", "", "This is publisher"),
                "",
                0,
                "This is a title",
                "",
                2700,
                false,
                "This is a description"
            ),
            imagePainter = ImageRequest.Builder(LocalContext.current).data(R.drawable.ic_microphone).build(),
            gradientColor = Color.DarkGray,
            yOffset = 0,
            playPauseIcon = R.drawable.ic_round_play_arrow,
            playbackProgress = 0f,
            currentTime = "0:00",
            totalTime = "10:00",
            darkTheme = true,
            onClose = { },
            onForward = { },
            onRewind = { },
            onTooglePlayback = { },
            onSliderChange = { },
            onSliderChangeFinished = { }
        )
    }
}