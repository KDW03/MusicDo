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

package com.najudoryeong.musicdo.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.componenet.DoCard
import com.najudoryeong.musicdo.core.designsystem.componenet.FavoriteButton
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.Song
import com.najudoryeong.musicdo.core.ui.R

/**
 * 전체 노래 목록 화면을 구성 composable
 *
 * songs.isnotEmpty ->  LazyColum을 사용하여 노래 목록 화면 표시
 * else -> EmptyContent 화면 표시
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Songs(
    songs: List<Song>,
    currentPlayingSongId: String,
    onClick: (Int) -> Unit,
    onToggleFavorite: (id: String, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (songs.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            // items : songs , key : song.mediaId
            itemsIndexed(items = songs, key = { _, song -> song.mediaId }) { index, song ->
                SongItem(
                    modifier = Modifier.animateItemPlacement(),
                    song = song,
                    isPlaying = song.mediaId == currentPlayingSongId,
                    onClick = { onClick(index) },
                    onToggleFavorite = { isFavorite -> onToggleFavorite(song.mediaId, isFavorite) },
                )
            }
        }
    } else {
        EmptyContent(textResource = R.string.no_songs)
    }
}

/**
 * LazyListScope 내에서 노래 목록이 다른 컨텐츠와 함께 섞여 표시될 때 사용 Composable
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.songs(
    songs: List<Song>,
    currentPlayingSongId: String,
    onClick: (Int) -> Unit,
    onToggleFavorite: (id: String, isFavorite: Boolean) -> Unit,
) {
    if (songs.isNotEmpty()) {
        itemsIndexed(items = songs, key = { _, song -> song.mediaId }) { index, song ->
            SongItem(
                modifier = Modifier.animateItemPlacement(),
                song = song,
                isPlaying = song.mediaId == currentPlayingSongId,
                onClick = { onClick(index) },
                onToggleFavorite = { isFavorite -> onToggleFavorite(song.mediaId, isFavorite) },
            )
        }
    } else {
        item {
            EmptyContent(textResource = R.string.no_songs)
        }
    }
}

/**
 *  노래 항목을 표시하는 @Composable 함수
 */
@Composable
private fun SongItem(
    song: Song,
    isPlaying: Boolean,
    onClick: () -> Unit,
    onToggleFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = if (isPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    ),
) {
    DoCard(modifier = modifier, onClick = onClick, shapeSize = 0.dp, colors = colors) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(SongDescriptionWeight),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.smallMedium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // song Artwork Image
                DoArtworkImage(
                    modifier = Modifier.size(SongCoverSize),
                    artworkUri = song.artworkUri,
                    contentDescription = song.title,
                )

                Column {
                    // song Title Text
                    SingleLineText(
                        text = song.title,
                        shouldUseMarquee = isPlaying,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isPlaying) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                    )
                    // song Artist Text
                    SingleLineText(
                        text = song.artist,
                        shouldUseMarquee = isPlaying,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isPlaying) {
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    )
                }
            }

            // isPlaying에 따른 favoriteButton Color 변경
            val favoriteButtonColor = if (isPlaying) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.primary
            }

            // song Favorite Button
            FavoriteButton(
                modifier = Modifier.weight(FavoriteButtonWeight),
                isFavorite = song.isFavorite,
                onToggleFavorite = onToggleFavorite,
                colors = IconButtonDefaults.iconToggleButtonColors(
                    contentColor = favoriteButtonColor,
                    checkedContentColor = favoriteButtonColor,
                ),
            )
        }
    }
}

private const val SongDescriptionWeight = 0.9f
private const val FavoriteButtonWeight = 0.1f
private val SongCoverSize = 50.dp
