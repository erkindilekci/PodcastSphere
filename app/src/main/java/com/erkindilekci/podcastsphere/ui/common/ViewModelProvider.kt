package com.erkindilekci.podcastsphere.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastDetailViewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastPlayerViewModel
import com.erkindilekci.podcastsphere.ui.viewmodel.PodcastSearchViewModel

//@Composable
//fun ProvideMultiViewModel(content: @Composable () -> Unit) {
//    val viewModel1: PodcastSearchViewModel = hiltViewModel()
//    val viewModel2: PodcastDetailViewModel = hiltViewModel()
//    val viewModel3: PodcastPlayerViewModel = hiltViewModel()
//
//    CompositionLocalProvider(
//        LocalPodcastSearchViewModel provides viewModel1,
//    ) {
//        CompositionLocalProvider(
//            LocalPodcastDetailViewModel provides viewModel2,
//        ) {
//            CompositionLocalProvider(
//                LocalPodcastPlayerViewModel provides viewModel3,
//            ) {
//                content()
//            }
//        }
//    }
//}

private val LocalPodcastSearchViewModel = staticCompositionLocalOf<PodcastSearchViewModel> {
    error("No PodcastSearchViewModel provided")
}

private val LocalPodcastDetailViewModel = staticCompositionLocalOf<PodcastDetailViewModel> {
    error("No PodcastDetailViewModel provided")
}

private val LocalPodcastPlayerViewModel = staticCompositionLocalOf<PodcastPlayerViewModel> {
    error("No PodcastPlayerViewModel provided")
}