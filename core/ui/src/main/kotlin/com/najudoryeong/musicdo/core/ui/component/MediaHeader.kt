package com.najudoryeong.musicdo.core.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.componenet.DoButton
import com.najudoryeong.musicdo.core.designsystem.componenet.DoOutlinedButton
import com.najudoryeong.musicdo.core.designsystem.componenet.RadioButtonText
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder
import com.najudoryeong.musicdo.core.ui.R
import com.najudoryeong.musicdo.core.media.common.R as mediaCommonR

/**
 *  미디어 헤더 UI를 구성하는 @Composable
 *
 *  if(AlbumHeader) MediaHeader
 *  else OutlinedMediaHeader
 */
@Composable
fun MediaHeader(
    sortOrder: SortOrder,
    sortBy: SortBy,
    onChangeSortOrder: (SortOrder) -> Unit,
    onChangeSortBy: (SortBy) -> Unit,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowSortSection by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            PlayShuffleButton(
                modifier = Modifier.weight(1f),
                iconResource = DoIcons.Play.resourceId,
                textResource = mediaCommonR.string.play,
                onClick = onPlayClick
            )

            PlayShuffleButton(
                modifier = Modifier.weight(1f),
                iconResource = DoIcons.Shuffle.resourceId,
                textResource = mediaCommonR.string.shuffle,
                onClick = onShuffleClick
            )

            SortButton(
                isSortSectionShown = shouldShowSortSection,
                onClick = { shouldShowSortSection = !shouldShowSortSection },
                colors = IconButtonDefaults.iconButtonColors(contentColor = MediaHeaderContentColor)
            )
        }


        // 정렬 순서 및 기준 표시 섹션 visible using shouldShowSortSection
        CompositionLocalProvider(LocalContentColor provides MediaHeaderContentColor) {
            AnimatedVisibility(visible = shouldShowSortSection) {
                SortSection(
                    sortOrder = sortOrder,
                    sortBy = sortBy,
                    onChangeSortOrder = onChangeSortOrder,
                    onChangeSortBy = onChangeSortBy,
                    radioButtonColors = RadioButtonDefaults.colors(
                        selectedColor = MediaHeaderContentColor,
                        unselectedColor = MediaHeaderContentColor.copy(alpha = 0.75f)
                    )
                )
            }
        }
    }
}

@Composable
fun OutlinedMediaHeader(
    sortOrder: SortOrder,
    sortBy: SortBy,
    onChangeSortOrder: (SortOrder) -> Unit,
    onChangeSortBy: (SortBy) -> Unit,
    onPlayClick: () -> Unit,
    onShuffleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowSortSection by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            PlayShuffleButton(
                modifier = Modifier.weight(1f),
                iconResource = DoIcons.Play.resourceId,
                textResource = mediaCommonR.string.play,
                onClick = onPlayClick
            )

            OutlinedPlayShuffleButton(
                modifier = Modifier.weight(1f),
                iconResource = DoIcons.Shuffle.resourceId,
                textResource = mediaCommonR.string.shuffle,
                onClick = onShuffleClick
            )

            SortButton(
                isSortSectionShown = shouldShowSortSection,
                onClick = { shouldShowSortSection = !shouldShowSortSection }
            )
        }

        AnimatedVisibility(visible = shouldShowSortSection) {
            SortSection(
                sortOrder = sortOrder,
                sortBy = sortBy,
                onChangeSortOrder = onChangeSortOrder,
                onChangeSortBy = onChangeSortBy
            )
        }
    }
}

/**
 * play and shuffle button in [MediaHeader] , [OutlinedMediaHeader]
 */
@Composable
private fun PlayShuffleButton(
    @DrawableRes iconResource: Int,
    @StringRes textResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DoButton(modifier = modifier, onClick = onClick) {
        Icon(
            modifier = Modifier.size(IconSize),
            painter = painterResource(id = iconResource),
            contentDescription = stringResource(id = textResource)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
        SingleLineText(text = stringResource(id = textResource), shouldUseMarquee = true)
    }
}

/**
 * play and shuffle button in [OutlinedMediaHeader]
 */
@Composable
private fun OutlinedPlayShuffleButton(
    @DrawableRes iconResource: Int,
    @StringRes textResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DoOutlinedButton(modifier = modifier, onClick = onClick) {
        Icon(
            modifier = Modifier.size(IconSize),
            painter = painterResource(id = iconResource),
            contentDescription = stringResource(id = textResource)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
        SingleLineText(text = stringResource(id = textResource), shouldUseMarquee = true)
    }
}

/**
 * 정렬 버튼 UI를 구성하는 @Composable 함
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SortButton(
    isSortSectionShown: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
) {

    // isSortSectionShown에 따른 회전 애니메이션
    val rotateValue by animateFloatAsState(
        targetValue = if (isSortSectionShown) SortRotateValue else 0f,
        label = "RotateAnimation"
    )

    IconButton(modifier = modifier, onClick = onClick, colors = colors) {
        AnimatedContent(
            modifier = Modifier.rotate(rotateValue), // 회전 애니메이션
            targetState = isSortSectionShown,
            transitionSpec = { scaleIn() with scaleOut() },
            label = "SortIconAnimation"
        ) { targetIsSortSectionShown ->
            if (targetIsSortSectionShown) {
                Icon(
                    imageVector = DoIcons.Close.imageVector,
                    contentDescription = stringResource(id = R.string.close)
                )
            } else {
                Icon(
                    painter = painterResource(id = DoIcons.Sort.resourceId),
                    contentDescription = stringResource(id = R.string.sort)
                )
            }
        }
    }
}

/**
 * 정렬 순서 및 기준 선택 @Composable
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SortSection(
    sortOrder: SortOrder,
    sortBy: SortBy,
    onChangeSortOrder: (SortOrder) -> Unit,
    onChangeSortBy: (SortBy) -> Unit,
    modifier: Modifier = Modifier,
    radioButtonColors: RadioButtonColors = RadioButtonDefaults.colors()
) {


    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            text = stringResource(id = R.string.sort_order),
            style = MaterialTheme.typography.titleMedium
        )

        // 오름차순 / 내림차순
        Row {
            RadioButtonText(
                modifier = Modifier.weight(1f),
                textRes = R.string.ascending,
                isSelected = sortOrder == SortOrder.ASCENDING,
                onClick = { onChangeSortOrder(SortOrder.ASCENDING) },
                colors = radioButtonColors
            )
            RadioButtonText(
                modifier = Modifier.weight(1f),
                textRes = R.string.descending,
                isSelected = sortOrder == SortOrder.DESCENDING,
                onClick = { onChangeSortOrder(SortOrder.DESCENDING) },
                colors = radioButtonColors
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            text = stringResource(id = R.string.sort_by),
            style = MaterialTheme.typography.titleMedium
        )

        /**
         * FlowRow maxItemsInEachRow = 2 , [RadioButtonText] FlowRow [SortBy]
         */
        FlowRow(maxItemsInEachRow = 2) {
            SortBy.values().forEach { sortByItem ->
                RadioButtonText(
                    modifier = Modifier.weight(1f),
                    textRes = sortByStringResourcesMap.getValue(sortByItem),
                    isSelected = sortBy == sortByItem,
                    onClick = { onChangeSortBy(sortByItem) },
                    colors = radioButtonColors
                )
            }
        }
    }
}

/**
 * SortBy 열거형 상수에 대응하는 문자열 리소스 ID Map
 */
private val sortByStringResourcesMap = mapOf(
    SortBy.TITLE to R.string.title,
    SortBy.ARTIST to R.string.artist,
    SortBy.ALBUM to R.string.album,
    SortBy.DURATION to R.string.duration,
    SortBy.DATE to R.string.date
)

private val IconSize = 20.dp
private val MediaHeaderContentColor = Color.White

private const val SortRotateValue = 180f
