package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.theme.DoTheme

@Composable
fun DoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) ButtonPressedScale else 1f,
        animationSpec = ButtonPressedAnimation,
        label = "ScaleAnimation",
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) ButtonPressedAlpha else 1f,
        animationSpec = ButtonPressedAnimation,
        label = "AlphaAnimation",
    )

    Button(
        modifier = modifier.graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha),
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}


@Composable
fun DoOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.outlinedShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = DoOutlinedBorder,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    DoButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}


@ThemePreviews
@Composable
fun DoButtonPreview() {
    DoTheme {
        DoBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            DoButton(
                onClick = {},
                content = {
                    Text("Do Button")
                },
            )
        }
    }
}


@ThemePreviews
@Composable
fun DoOutlinedButtonPreview() {
    DoTheme {
        DoBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            DoOutlinedButton(
                onClick = {},
                content = {
                    Text("Do Button")
                },
            )
        }
    }
}


private const val ButtonPressedScale = 0.95f
private const val ButtonPressedAlpha = 0.75f
private val ButtonPressedAnimation = tween<Float>()
