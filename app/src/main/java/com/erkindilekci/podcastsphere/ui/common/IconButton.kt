package com.erkindilekci.podcastsphere.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    tint: Color = LocalContentColor.current.copy(alpha = 0.38f),
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(padding)
    )
}