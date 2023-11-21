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

package com.najudoryeong.musicdo.feature.player.mini

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.ui.component.DoArtworkImage

@Composable
internal fun MiniPlayerArtworkImage(
    artworkUri: Uri,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    DoArtworkImage(
        modifier = modifier.size(ArtworkImageSize),
        artworkUri = artworkUri,
        contentDescription = contentDescription,
        shape = shape,
    )
}

private val ArtworkImageSize = 40.dp
