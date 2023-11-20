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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.lerp
import com.najudoryeong.musicdo.core.designsystem.componenet.DoTab
import com.najudoryeong.musicdo.core.designsystem.componenet.DoTabRow
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.Album
import com.najudoryeong.musicdo.core.model.Artist
import com.najudoryeong.musicdo.core.model.Folder
import com.najudoryeong.musicdo.core.model.Song
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder
import com.najudoryeong.musicdo.core.ui.common.MediaTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaPager(
    songs: List<Song>,
    currentPlayingSongId: String,
    artists: List<Artist>,
    albums: List<Album>,
    folders: List<Folder>,
    sortOrder: SortOrder,
    sortBy: SortBy,
    onChangeSortOrder: (SortOrder) -> Unit,
    onChangeSortBy: (SortBy) -> Unit,
    onSongClick: (Int) -> Unit,
    onArtistClick: (Long) -> Unit,
    onAlbumClick: (Long) -> Unit,
    onFolderClick: (String) -> Unit,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onToggleFavorite: (id: String, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val tabs = remember { MediaTab.values() }
    val pagerState = rememberPagerState()
    val selectedTabIndex = pagerState.currentPage

    Column(modifier = modifier) {
        DoTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                )
            },
        ) {
            tabs.forEach { tab ->
                val index = tab.ordinal
                val selected = selectedTabIndex == index

                DoTab(
                    selected = selected,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = stringResource(id = tab.titleResource)) },
                )
            }
        }

        /**
         *  Songs가 비어있지 않다면 MediaHeader 노출
         */
        AnimatedVisibility(visible = songs.isNotEmpty()) {
            OutlinedMediaHeader(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                sortOrder = sortOrder,
                sortBy = sortBy,
                onChangeSortOrder = onChangeSortOrder,
                onChangeSortBy = onChangeSortBy,
                onPlayClick = onPlayClick,
                onShuffleClick = onShuffleClick,
            )
        }

        /**
         * 가로 방향으로 스와이프하여 다양한 페이지를 표시하는 @Composable
         */
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            pageCount = tabs.size,
            verticalAlignment = Alignment.Top,
        ) { page ->
            // 현재 페이지에 따른 다른 컨텐츠를 표시
            when (page) {
                MediaTab.Songs.ordinal -> {
                    Songs(
                        songs = songs,
                        currentPlayingSongId = currentPlayingSongId,
                        onClick = onSongClick,
                        onToggleFavorite = onToggleFavorite,
                    )
                }

                MediaTab.Artists.ordinal -> {
                    Artists(artists = artists, onClick = onArtistClick)
                }

                MediaTab.Albums.ordinal -> {
                    Albums(albums = albums, onClick = onAlbumClick)
                }

                MediaTab.Folders.ordinal -> {
                    Folders(folders = folders, onClick = onFolderClick)
                }
            }
        }
    }
}

/**
 * [HorizontalPager] 에서 탭 인디케이터의 위치와 크기를 동적으로 계산하여 애니메이션 효과를 제공하는 Modifier 확장 함수
 */
@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    pageIndexMapping: (Int) -> Int = { it },
) = layout { measurable, constraints ->
    if (tabPositions.isEmpty()) {
        layout(constraints.maxWidth, 0) {}
    } else {
        val currentPage = minOf(tabPositions.lastIndex, pageIndexMapping(pagerState.currentPage))
        val currentTab = tabPositions[currentPage]
        val previousTab = tabPositions.getOrNull(currentPage - 1)
        val nextTab = tabPositions.getOrNull(currentPage + 1)
        val fraction = pagerState.currentPageOffsetFraction // 현재 페이지 오프셋

        /**
         * indicator 너비 계산
         * lerp : 선형 보간, 두 값 사이를 부드럽게 전환
         */
        val indicatorWidth = if (fraction > 0 && nextTab != null) { // 오른쪽 스와이프
            lerp(currentTab.width, nextTab.width, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) { // 왼쪽 스와이프
            lerp(currentTab.width, previousTab.width, -fraction).roundToPx()
        } else { // 스와이프 x
            currentTab.width.roundToPx()
        }

        // indicator 위치 계산
        val indicatorOffset = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.left, nextTab.left, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.left, previousTab.left, -fraction).roundToPx()
        } else {
            currentTab.left.roundToPx()
        }

        val placeable = measurable.measure(
            Constraints(
                minWidth = indicatorWidth,
                maxWidth = indicatorWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight,
            ),
        )
        layout(constraints.maxWidth, maxOf(placeable.height, constraints.minHeight)) {
            placeable.placeRelative(
                indicatorOffset,
                maxOf(constraints.minHeight - placeable.height, 0),
            )
        }
    }
}
