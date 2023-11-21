package com.najudoryeong.musicdo.feature.player.component

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.util.lerp
import coil.request.ImageRequest
import com.najudoryeong.musicdo.core.designsystem.componenet.DoImage
import com.najudoryeong.musicdo.core.designsystem.componenet.DoOverlay
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.Song
import com.najudoryeong.musicdo.core.ui.component.DoArtworkImage
import com.najudoryeong.musicdo.feature.player.util.BlurTransformation
import kotlin.math.absoluteValue

/**
 * 플레이어 백그라운드 아트워크 오버레이 @Composable
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PlayerBackdropArtworkOverlay(
    playingQueueSongs: List<Song>,
    currentSongIndex: Int,
    currentMediaId: String,
    onSkipToIndex: (Int) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = currentSongIndex)
    val currentSong = playingQueueSongs.getOrNull(currentSongIndex)

    // 현재 노래, 미디어 ID, 인덱스 변경 시
    LaunchedEffect(currentSong, currentMediaId, currentSongIndex) {
        if (currentSong?.mediaId != currentMediaId) return@LaunchedEffect

        // 적절한 페이지로 스크롤
        if (currentSongIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(page = currentSongIndex)
        }
    }

    //페이저 스크롤시 현재 페이지 감지 후 이동
    LaunchedEffect(
        currentSong,
        currentMediaId,
        pagerState.currentPage,
        pagerState.isScrollInProgress,
    ) {
        if (currentSong?.mediaId != currentMediaId) return@LaunchedEffect

        val currentPage = pagerState.currentPage
        if (currentSongIndex != currentPage && !pagerState.isScrollInProgress) {
            onSkipToIndex(currentPage)
        }
    }

    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.scrim)
            .fillMaxSize(),
    ) {
        Crossfade(
            targetState = currentSong?.artworkUri.orEmpty(),
            label = "BackdropArtworkImageAnimation",
        ) { artworkUri ->
            // Background Blur Artwork Image using Overlay
            Box {
                PlayerBackdropArtworkImage(
                    artworkUri = artworkUri,
                    contentDescription = currentSong?.title,
                )
                DoOverlay(
                    color = MaterialTheme.colorScheme.scrim,
                    alpha = BackdropArtworkOverlayAlpha,
                )
            }
        }

        // Front Artwork Image with other content
        Column(
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.medium)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            HorizontalPager(
                state = pagerState,
                pageCount = playingQueueSongs.size.coerceAtLeast(1),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.extraLarge),
            ) { page ->
                PlayerFrontArtworkImage(
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = pagerState.calculatePageOffset(page)
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f),
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f),
                            )
                        },
                    artworkUri = playingQueueSongs.getOrNull(page)?.artworkUri.orEmpty(),
                    contentDescription = currentSong?.title,
                )
            }
            content()
        }
    }
}


@Composable
private fun PlayerFrontArtworkImage(
    artworkUri: Uri,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.Transparent),
    contentScale: ContentScale = ContentScale.Crop,
) {
    DoArtworkImage(
        modifier = modifier.aspectRatio(1f),
        artworkUri = artworkUri,
        contentDescription = contentDescription,
        shape = shape,
        colors = colors,
        contentScale = contentScale,
    )
}


@Composable
private fun PlayerBackdropArtworkImage(
    artworkUri: Uri,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    contentScale: ContentScale = ContentScale.Crop,
) {
    DoImage(
        modifier = modifier.fillMaxSize(),
        model = ImageRequest.Builder(LocalContext.current)
            .data(artworkUri)
            .transformations(BlurTransformation())
            .build(),
        contentDescription = contentDescription,
        shape = shape,
        colors = colors,
        contentScale = contentScale,
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun PagerState.calculatePageOffset(page: Int) =
    ((currentPage - page) + currentPageOffsetFraction).absoluteValue

private fun Uri?.orEmpty() = this ?: Uri.EMPTY

private const val BackdropArtworkOverlayAlpha = 0.2f
