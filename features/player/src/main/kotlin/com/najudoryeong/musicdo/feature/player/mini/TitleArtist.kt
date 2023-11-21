package com.najudoryeong.musicdo.feature.player.mini

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText

@Composable
internal fun MiniPlayerTitleArtist(title: String, artist: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        SingleLineText(
            text = title,
            shouldUseMarquee = true,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        SingleLineText(
            text = artist,
            shouldUseMarquee = true,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
