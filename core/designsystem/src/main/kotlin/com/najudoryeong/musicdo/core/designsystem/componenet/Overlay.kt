package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.najudoryeong.musicdo.core.designsystem.theme.DoTheme

@Composable
fun DoOverlay(
    color: Color,
    alpha: Float,
    modifier: Modifier = Modifier,
    brush: Brush = Brush.verticalGradient(listOf(color.copy(alpha = alpha), color)),
    shape: Shape = RectangleShape,
) {
    Box(
        modifier = modifier
            .background(brush = brush, shape = shape)
            .fillMaxSize(),
    )
}


@ThemePreviews
@Composable
fun DoOverlayPreview() {
    val sampleAlpha = 0.5f
    DoTheme {
        DoBackground {
            DoOverlay(
                modifier = Modifier.aspectRatio(1f),
                color = MaterialTheme.colorScheme.scrim,
                alpha = sampleAlpha,
            )
        }
    }
}