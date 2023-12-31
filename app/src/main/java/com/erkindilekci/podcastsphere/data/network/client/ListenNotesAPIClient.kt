package com.erkindilekci.podcastsphere.data.network.client

import com.erkindilekci.podcastsphere.data.network.constant.ListenNotesAPI
import com.erkindilekci.podcastsphere.data.network.service.PodcastService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ListenNotesAPIClient {
    fun createHttpClient(): OkHttpClient {
        val requestInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("X-ListenAPI-Key", "YOUR_API_KEY")
                .build()

            return@Interceptor chain.proceed(request)
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)

        return httpClient.build()
    }

    fun createPodcastService(
        client: OkHttpClient
    ): PodcastService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(ListenNotesAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PodcastService::class.java)
    }
}