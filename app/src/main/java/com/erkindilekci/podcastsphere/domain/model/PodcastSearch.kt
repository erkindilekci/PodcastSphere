package com.erkindilekci.podcastsphere.domain.model

import com.erkindilekci.podcastsphere.domain.model.Episode

data class PodcastSearch(
    val count: Long,
    val total: Long,
    val results: List<Episode>
)