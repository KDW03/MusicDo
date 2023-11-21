package com.najudoryeong.musicdo.feature.player.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.componenet.FavoriteButton
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.PlaybackMode
import com.najudoryeong.musicdo.core.media.common.R

/**
 * FullPlayer MediaButtons
 * (playback , skipPrevious, Play, skipNext, Favorite) Buttons
 */
@Composable
internal fun PlayerMediaButtons(
    isPlaying: Boolean,
    playbackMode: PlaybackMode,
    isFavorite: Boolean,
    onPlaybackModeClick: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onToggleFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PlaybackModeMediaButton(playbackMode = playbackMode, onClick = onPlaybackModeClick)

        MediaButton(
            iconResource = DoIcons.SkipPrevious.resourceId,
            contentDescriptionResource = R.string.skip_previous,
            onClick = onSkipPreviousClick
        )

        // isPlaying에 따른 spring 애니메이션 효과
        Crossfade(
            targetState = isPlaying,
            animationSpec = spring(),
            label = "CrossfadeAnimation"
        ) { targetIsPlaying ->
            PlayPauseMediaButton(
                isPlaying = targetIsPlaying,
                playIconResource = DoIcons.Play.resourceId,
                playContentDescriptionResource = R.string.play,
                onPlayClick = onPlayClick,
                pauseIconResource = DoIcons.Pause.resourceId,
                pauseContentDescriptionResource = R.string.pause,
                onPauseClick = onPauseClick
            )
        }

        MediaButton(
            iconResource = DoIcons.SkipNext.resourceId,
            contentDescriptionResource = R.string.skip_next,
            onClick = onSkipNextClick
        )

        FavoriteButton(
            iconModifier = Modifier.size(MediaButtonIconSize),
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite,
            colors = IconButtonDefaults.iconToggleButtonColors(
                contentColor = MediaButtonIconColor,
                checkedContentColor = MediaButtonIconColor
            )
        )
    }
}

/**
 * Player media Button @Composable
 */
@Composable
private fun MediaButton(
    @DrawableRes iconResource: Int,
    @StringRes contentDescriptionResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(contentColor = MediaButtonIconColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) MediaButtonPressedScale else 1f,
        animationSpec = MediaButtonPressedAnimation,
        label = "ScaleAnimation"
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) MediaButtonPressedAlpha else 1f,
        animationSpec = MediaButtonPressedAnimation,
        label = "AlphaAnimation"
    )

    IconButton(
        modifier = modifier.graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha),
        onClick = onClick,
        colors = colors,
        interactionSource = interactionSource
    ) {
        Icon(
            modifier = Modifier.size(MediaButtonIconSize),
            painter = painterResource(id = iconResource),
            contentDescription = stringResource(id = contentDescriptionResource)
        )
    }
}

@Composable
private fun PlayPauseMediaButton(
    isPlaying: Boolean,
    @DrawableRes playIconResource: Int,
    @StringRes playContentDescriptionResource: Int,
    onPlayClick: () -> Unit,
    @DrawableRes pauseIconResource: Int,
    @StringRes pauseContentDescriptionResource: Int,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    color: Color = MediaButtonIconColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication = LocalIndication.current
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) MediaButtonPressedScale else 1f,
        animationSpec = MediaButtonPressedAnimation,
        label = "ScaleAnimation"
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) MediaButtonPressedAlpha else 1f,
        animationSpec = MediaButtonPressedAnimation,
        label = "AlphaAnimation"
    )

    Box(
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha)
            .clip(shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = if (isPlaying) onPlayClick else onPauseClick
            )
    ) {
        Icon(
            modifier = Modifier
                .background(
                    brush = SolidColor(MediaButtonBackgroundColor),
                    shape = shape,
                    alpha = MediaButtonBackgroundAlpha
                )
                .padding(MaterialTheme.spacing.medium)
                .size(MediaButtonIconSize),
            painter = painterResource(
                id = if (isPlaying) playIconResource else pauseIconResource
            ),
            contentDescription = stringResource(
                id = if (isPlaying) playContentDescriptionResource else pauseContentDescriptionResource
            ),
            tint = color
        )
    }
}

@Composable
private fun PlaybackModeMediaButton(
    playbackMode: PlaybackMode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconResource = when (playbackMode) {
        PlaybackMode.REPEAT -> DoIcons.Repeat
        PlaybackMode.REPEAT_ONE -> DoIcons.RepeatOne
        PlaybackMode.SHUFFLE -> DoIcons.Shuffle
    }.resourceId

    val contentDescriptionResource = when (playbackMode) {
        PlaybackMode.REPEAT -> R.string.repeat
        PlaybackMode.REPEAT_ONE -> R.string.repeat_one
        PlaybackMode.SHUFFLE -> R.string.shuffle
    }

    MediaButton(
        modifier = modifier,
        iconResource = iconResource,
        contentDescriptionResource = contentDescriptionResource,
        onClick = onClick
    )
}

private const val MediaButtonPressedScale = 0.85f
private const val MediaButtonPressedAlpha = 0.75f
private val MediaButtonPressedAnimation = tween<Float>()
private val MediaButtonIconSize = 32.dp
private val MediaButtonIconColor = Color.White
private val MediaButtonBackgroundColor = Color.Gray
private const val MediaButtonBackgroundAlpha = 0.5f
