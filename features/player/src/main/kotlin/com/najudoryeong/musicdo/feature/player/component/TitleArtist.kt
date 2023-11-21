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

package com.najudoryeong.musicdo.feature.player.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.theme.spacing

@Composable
internal fun PlayerTitleArtist(title: String, artist: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
        SingleLineText(
            text = title,
            shouldUseMarquee = true,
            style = MaterialTheme.typography.titleLarge,
            color = TitleColor,
        )
        SingleLineText(
            text = artist,
            shouldUseMarquee = true,
            style = MaterialTheme.typography.titleMedium,
            color = ArtistColor,
        )
    }
}

private val TitleColor = Color.White
private const val ArtistColorAlpha = 0.75f
private val ArtistColor = TitleColor.copy(ArtistColorAlpha)
