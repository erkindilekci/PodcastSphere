package com.erkindilekci.podcastsphere.domain.repository

import com.erkindilekci.podcastsphere.data.datastore.PodcastDataStore
import com.erkindilekci.podcastsphere.data.network.service.PodcastService
import com.erkindilekci.podcastsphere.domain.model.PodcastSearch
import com.erkindilekci.podcastsphere.error.Failure
import com.erkindilekci.podcastsphere.util.Either
import com.erkindilekci.podcastsphere.util.left
import com.erkindilekci.podcastsphere.util.right

class PodcastRepositoryImpl(
    private val service: PodcastService,
    private val dataStore: PodcastDataStore
) : PodcastRepository {

    companion object {
        private const val TAG = "PodcastRepository"
    }

    override suspend fun searchPodcasts(
        query: String,
        type: String
    ): Either<Failure, PodcastSearch> {
        return try {
            val canFetchAPI = dataStore.canFetchAPI()
            if (canFetchAPI) {
                val result = service.searchPodcasts(query, type).asDomainModel()
                dataStore.storePodcastSearchResult(result)
                right(result)
            } else {
                right(dataStore.readLastPodcastSearchResult())
            }
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }
}