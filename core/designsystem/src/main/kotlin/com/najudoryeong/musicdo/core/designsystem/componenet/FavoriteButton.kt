/*
 * Copyright 2023 KDW03
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.R
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.theme.DoTheme

/**
 * 즐겨찾기 토글 버튼을 구현하는 @Composable
 */
@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onToggleFavorite: (isFavorite: Boolean) -> Unit,
    iconModifier: Modifier = Modifier,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(
        contentColor = MaterialTheme.colorScheme.primary,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    /**
     * 버튼이 눌렸을 때 스케일 및 투명도 애니메이션
     */
    val scale by animateFloatAsState(
        targetValue = if (isPressed) FavoriteButtonPressedScale else 1f,
        animationSpec = FavoriteButtonPressedAnimation,
        label = "ScaleAnimation",
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) FavoriteButtonPressedAlpha else 1f,
        animationSpec = FavoriteButtonPressedAnimation,
        label = "AlphaAnimation",
    )

    val imageVector =
        if (isFavorite) DoIcons.Favorite.imageVector else DoIcons.FavoriteBorder.imageVector
    val contentDescriptionResource =
        if (isFavorite) R.string.favorite_remove else R.string.favorite_add

    IconToggleButton(
        modifier = modifier.graphicsLayer(scaleX = scale, scaleY = scale, alpha = alpha),
        checked = isFavorite,
        onCheckedChange = onToggleFavorite,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = imageVector,
            contentDescription = stringResource(id = contentDescriptionResource),
        )
    }
}

private const val FavoriteButtonPressedScale = 0.85f
private const val FavoriteButtonPressedAlpha = 0.75f
private val FavoriteButtonPressedAnimation = tween<Float>()

@ThemePreviews
@Composable
fun FavoriteButtonPreview() {
    DoTheme {
        DoBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            FavoriteButton(onToggleFavorite = {})
        }
    }
}
