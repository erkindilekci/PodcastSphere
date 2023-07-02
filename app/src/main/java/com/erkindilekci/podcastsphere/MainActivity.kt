package com.erkindilekci.podcastsphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.erkindilekci.podcastsphere.constant.Constants
import com.erkindilekci.podcastsphere.ui.home.HomeScreen
import com.erkindilekci.podcastsphere.ui.navigation.Destination
import com.erkindilekci.podcastsphere.ui.podcast.PodcastBottomBar
import com.erkindilekci.podcastsphere.ui.podcast.PodcastDetailScreen
import com.erkindilekci.podcastsphere.ui.podcast.PodcastPlayerScreen
import com.erkindilekci.podcastsphere.ui.theme.PodcastSphereTheme
import com.erkindilekci.podcastsphere.ui.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PodcastSphere)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        var startDestination = Destination.home
        if (intent?.action == Constants.ACTION_PODCAST_NOTIFICATION_CLICK) {
            startDestination = Destination.home
        }

        setContent {
            PodcastApp(
                startDestination = startDestination,
                backDispatcher = onBackPressedDispatcher
            )
        }
    }
}

@Composable
fun PodcastApp(
    startDestination: String = Destination.welcome,
    backDispatcher: OnBackPressedDispatcher
) {
    PodcastSphereTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val navController = rememberNavController()
            NavHost(navController, startDestination) {
                composable(Destination.welcome) { WelcomeScreen(navController) }

                composable(Destination.home) {
                    HomeScreen(navController)
                }

                composable(
                    Destination.podcast,
                    deepLinks = listOf(navDeepLink {
                        uriPattern = "https://www.listennotes.com/e/{id}"
                    })
                ) { backStackEntry ->
                    PodcastDetailScreen(
                        podcastId = backStackEntry.arguments?.getString("id")!!,
                    )
                }
            }
            PodcastBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            PodcastPlayerScreen(backDispatcher)
        }
    }
}
