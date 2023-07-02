package com.erkindilekci.podcastsphere.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.podcastsphere.R

@Composable
fun BackButton() {
    val navController = rememberNavController()
    Icon(
        Icons.Default.ArrowBack,
        contentDescription = stringResource(R.string.back),
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(CircleShape)
            .clickable {
                navController.navigateUp()
            }
            .padding(16.dp)
    )
}