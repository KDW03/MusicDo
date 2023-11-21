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
    shape: Shape = CircleShape
) {
    DoArtworkImage(
        modifier = modifier.size(ArtworkImageSize),
        artworkUri = artworkUri,
        contentDescription = contentDescription,
        shape = shape
    )
}

private val ArtworkImageSize = 40.dp
