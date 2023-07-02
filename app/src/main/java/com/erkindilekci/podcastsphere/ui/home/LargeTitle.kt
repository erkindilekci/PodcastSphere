package com.erkindilekci.podcastsphere.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erkindilekci.podcastsphere.R
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun LargeTitle() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .height(120.dp)
    ) {
        Text(
            stringResource(R.string.trending_now),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}