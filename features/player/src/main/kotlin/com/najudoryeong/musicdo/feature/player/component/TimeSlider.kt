package com.najudoryeong.musicdo.feature.player.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.feature.player.util.asFormattedString
import com.najudoryeong.musicdo.feature.player.util.convertToProgress


/**
 * FullPlayer TimeBark
 */
@Composable
internal fun PlayerTimeSlider(
    currentPosition: Long,
    duration: Long,
    onSkipTo: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    // 진행 비율 애니메이션
    val progress by animateFloatAsState(
        targetValue = convertToProgress(count = currentPosition, total = duration),
        label = "ProgressAnimation"
    )

    Column(
        modifier = modifier
            .padding(horizontal = MaterialTheme.spacing.medium)
            .fillMaxWidth()
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = progress,
            onValueChange = onSkipTo
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentPosition.asFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = TimeTextColor
            )
            Text(
                text = duration.asFormattedString(),
                style = MaterialTheme.typography.bodySmall,
                color = TimeTextColor
            )
        }
    }
}

private val TimeTextColor = Color.Gray
