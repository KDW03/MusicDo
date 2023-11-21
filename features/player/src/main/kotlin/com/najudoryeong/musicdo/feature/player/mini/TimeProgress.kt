package com.najudoryeong.musicdo.feature.player.mini

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.PlaybackState
import com.najudoryeong.musicdo.feature.player.util.asFormattedString
import com.najudoryeong.musicdo.feature.player.util.convertToProgress

@Composable
internal fun MiniPlayerTimeProgress(
    playbackState: PlaybackState,
    currentPosition: Long,
    duration: Long,
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = convertToProgress(count = currentPosition, total = duration),
        label = "ProgressAnimation"
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentPosition.asFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = duration.asFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (playbackState == PlaybackState.BUFFERING) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), progress = progress)
        }
    }
}
