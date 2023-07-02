package com.erkindilekci.podcastsphere.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.podcastsphere.domain.model.Episode
import com.erkindilekci.podcastsphere.ui.common.PreviewContent
import com.erkindilekci.podcastsphere.ui.common.StaggeredVerticalGrid
import com.erkindilekci.podcastsphere.ui.navigation.Destination
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastPlayerViewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastSearchViewModel
import com.erkindilekci.podcastsphere.util.Resource
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun HomeScreen(navController: NavHostController) {
    val scrollState = rememberLazyListState()
    val podcastSearchViewModel = hiltViewModel<PodcastSearchViewModel>()
    val podcastSearch = podcastSearchViewModel.podcastSearch

    Surface {
        LazyColumn(state = scrollState) {
            item {
                LargeTitle()
            }

            when (podcastSearch) {
                is Resource.Error -> {
                    item {
                        ErrorView(text = podcastSearch.failure.translate()) {
                            podcastSearchViewModel.searchPodcasts()
                        }
                    }
                }

                Resource.Loading -> {
                    item {
                        LoadingPlaceholder()
                    }
                }

                is Resource.Success -> {
                    item {
                        StaggeredVerticalGrid(
                            crossAxisCount = 2,
                            spacing = 16.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            podcastSearch.data.results.forEach { podcast ->
                                PodcastView(
                                    podcast = podcast,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                ) {
                                    openPodcastDetail(navController, podcast)
                                }
                            }
                        }
                    }
                }
            }

            item {
                val playerViewModel: PodcastPlayerViewModel = hiltViewModel()
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 32.dp)
                        .padding(bottom = if (playerViewModel.currentPlayingEpisode.value != null) 64.dp else 0.dp)
                )
            }
        }
    }
}

private fun openPodcastDetail(
    navController: NavHostController,
    podcast: Episode
) {
    navController.navigate(Destination.podcast(podcast.id)) { }
}

@Composable
@Preview(name = "Home")
fun HomeScreenPreview() {
    PreviewContent {
        HomeScreen(rememberNavController())
    }
}

@Composable
@Preview(name = "Home (Dark)")
fun HomeScreenDarkPreview() {
    PreviewContent(darkTheme = true) {
        HomeScreen(rememberNavController())
    }
}