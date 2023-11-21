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
            color = TitleColor
        )
        SingleLineText(
            text = artist,
            shouldUseMarquee = true,
            style = MaterialTheme.typography.titleMedium,
            color = ArtistColor
        )
    }
}

private val TitleColor = Color.White
private const val ArtistColorAlpha = 0.75f
private val ArtistColor = TitleColor.copy(ArtistColorAlpha)
