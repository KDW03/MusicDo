package com.najudoryeong.musicdo.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import com.najudoryeong.musicdo.core.designsystem.componenet.DoCard
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.Artist
import com.najudoryeong.musicdo.core.ui.R

/**
 * 아티스트 목록을 표시하는 @Composable
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Artists(
    artists: List<Artist>,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (artists.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(items = artists, key = Artist::id) { artist ->
                ArtistItem(
                    modifier = Modifier.animateItemPlacement(),
                    artist = artist,
                    onClick = { onClick(artist.id) }
                )
            }
        }
    } else {
        EmptyContent(textResource = R.string.no_artists)
    }
}

@Composable
private fun ArtistItem(
    artist: Artist,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DoCard(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth()
        ) {
            // Artis name Text
            SingleLineText(
                text = artist.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Artis song size Text
            SingleLineText(
                text = pluralStringResource(
                    id = R.plurals.number_of_songs,
                    count = artist.songs.size,
                    artist.songs.size
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
