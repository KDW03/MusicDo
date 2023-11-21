package com.najudoryeong.musicdo.feature.player.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.najudoryeong.musicdo.feature.player.R
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

/**
 * Long 타입의 시간값(밀리초 단위)을 ("mm:ss")로 변환하는 확장함수
 * EX) 903000 -> 15:03
 */
@Composable
internal fun Long.asFormattedString() = milliseconds.toComponents { minutes, seconds, _ ->
    stringResource(
        id = R.string.player_timestamp_format,
        String.format(locale = Locale.US, format = "%02d", minutes),
        String.format(locale = Locale.US, format = "%02d", seconds)
    )
}

/**
 *  현재 재생 시간과 총 재생 시간을 바탕으로 진행 상태를 계산하는 함수
 *  현재 재생 시간(count)를 총 재생 시간(total)으로 나눈 후, 100으로 나눠서 진행률을 계산
 */
internal fun convertToProgress(count: Long, total: Long) =
    ((count * ProgressDivider) / total / ProgressDivider).takeIf(Float::isFinite) ?: ZeroProgress

/**
 * 플레이어 progressbar 사용자가 선택한 위치를 실제 재생 시간으로 변환하는 함수
 */
internal fun convertToPosition(value: Float, total: Long) = (value * total).toLong()

private const val ProgressDivider = 100f
private const val ZeroProgress = 0f
