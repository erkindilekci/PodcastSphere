package com.erkindilekci.podcastsphere.ui.podcast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erkindilekci.podcastsphere.R
import com.erkindilekci.podcastsphere.ui.common.BackButton
import com.erkindilekci.podcastsphere.ui.common.EmphasisText
import com.erkindilekci.podcastsphere.ui.common.IconButton
import com.erkindilekci.podcastsphere.ui.common.PrimaryButton
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastDetailViewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastPlayerViewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastSearchViewModel
import com.erkindilekci.podcastsphere.util.Resource
import com.erkindilekci.podcastsphere.util.formatMillisecondsAsDate
import com.erkindilekci.podcastsphere.util.toDurationMinutes
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun PodcastDetailScreen(
    podcastId: String
) {
    val scrollState = rememberScrollState()
    val podcastSearchViewModel = hiltViewModel<PodcastSearchViewModel>()
    val detailViewModel = hiltViewModel<PodcastDetailViewModel>()
    val playerViewModel = hiltViewModel<PodcastPlayerViewModel>()
    val podcast = podcastSearchViewModel.getPodcastDetail(podcastId)
    val currentContext = LocalContext.current

    Surface {
        Column(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            Row {
                BackButton()
            }

            if (podcast != null) {
                val playButtonText =
                    if (playerViewModel.podcastIsPlaying &&
                        playerViewModel.currentPlayingEpisode.value?.id == podcast.id
                    ) stringResource(R.string.pause) else stringResource(R.string.play)

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .navigationBarsPadding()
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                        .padding(bottom = if (playerViewModel.currentPlayingEpisode.value != null) 64.dp else 0.dp)

                ) {
                    PodcastImage(
                        url = podcast.image,
                        modifier = Modifier.height(120.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        podcast.titleOriginal,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        podcast.podcast.publisherOriginal,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    EmphasisText(
                        text = "${podcast.pubDateMS.formatMillisecondsAsDate("MMM dd")} • ${podcast.audioLengthSec.toDurationMinutes()}"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        PrimaryButton(
                            text = playButtonText,
                            height = 48.dp
                        ) {
                            playerViewModel.playPodcast(
                                (podcastSearchViewModel.podcastSearch as Resource.Success).data.results,
                                podcast
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = stringResource(R.string.share)
                        ) {
                            detailViewModel.sharePodcastEpidose(currentContext, podcast)
                        }

                        IconButton(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = stringResource(R.string.source_web)
                        ) {
                            detailViewModel.openListenNotesURL(currentContext, podcast)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    EmphasisText(text = podcast.descriptionOriginal)
                }
            }
        }
    }
}
