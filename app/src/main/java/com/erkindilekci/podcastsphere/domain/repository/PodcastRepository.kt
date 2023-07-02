package com.erkindilekci.podcastsphere.domain.repository

import com.erkindilekci.podcastsphere.domain.model.PodcastSearch
import com.erkindilekci.podcastsphere.error.Failure
import com.erkindilekci.podcastsphere.util.Either

interface PodcastRepository {

    suspend fun searchPodcasts(
        query: String,
        type: String,
    ): Either<Failure, PodcastSearch>
}