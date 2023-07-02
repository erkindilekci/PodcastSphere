package com.erkindilekci.podcastsphere.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.erkindilekci.podcastsphere.R

@Composable
fun ErrorView(
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Text(
            text,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        )
        TextButton(onClick = onClick) {
            Text(
                stringResource(R.string.try_again),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}