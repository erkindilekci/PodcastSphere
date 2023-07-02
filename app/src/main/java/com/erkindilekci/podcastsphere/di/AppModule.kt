package com.erkindilekci.podcastsphere.di

import android.content.Context
import com.erkindilekci.podcastsphere.data.datastore.PodcastDataStore
import com.erkindilekci.podcastsphere.data.exoplayer.PodcastMediaSource
import com.erkindilekci.podcastsphere.data.network.client.ListenNotesAPIClient
import com.erkindilekci.podcastsphere.data.network.service.PodcastService
import com.erkindilekci.podcastsphere.data.service.MediaPlayerServiceConnection
import com.erkindilekci.podcastsphere.domain.repository.PodcastRepository
import com.erkindilekci.podcastsphere.domain.repository.PodcastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpClient(): OkHttpClient = ListenNotesAPIClient.createHttpClient()

    @Provides
    @Singleton
    fun providePodcastService(
        client: OkHttpClient
    ): PodcastService = ListenNotesAPIClient.createPodcastService(client)

    @Provides
    @Singleton
    fun providePodcastDataStore(
        @ApplicationContext context: Context
    ): PodcastDataStore = PodcastDataStore(context)

    @Provides
    @Singleton
    fun providePodcastRepository(
        service: PodcastService,
        dataStore: PodcastDataStore
    ): PodcastRepository = PodcastRepositoryImpl(service, dataStore)

    @Provides
    @Singleton
    fun provideMediaPlayerServiceConnection(
        @ApplicationContext context: Context,
        mediaSource: PodcastMediaSource
    ): MediaPlayerServiceConnection = MediaPlayerServiceConnection(context, mediaSource)
}