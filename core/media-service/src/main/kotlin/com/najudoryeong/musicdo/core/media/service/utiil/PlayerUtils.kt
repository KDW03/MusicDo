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

package com.najudoryeong.musicdo.core.media.service.utiil

import androidx.media3.common.C
import androidx.media3.common.Player
import com.najudoryeong.musicdo.core.media.common.MediaConstants.DEFAULT_DURATION_MS
import com.najudoryeong.musicdo.core.model.PlaybackState

/**
 * Player의 playbackState Int값을 PlaybackState 열거형으로 변환하는 확장 함수.
 *
 * @return PlaybackState - 변환된 재생 상태.
 * @throws IllegalStateException - Int 값이 유효한 재생 상태를 나타내지 않는 경우.
 */
internal fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState.IDLE
    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
    Player.STATE_READY -> PlaybackState.READY
    Player.STATE_ENDED -> PlaybackState.ENDED
    else -> error("Invalid playback state.")
}

/**
 *  미디어 재생 시간 Long 타입의 타임스탬프 값을 반환하는 확장 함수.
 * @return Long - 타임스탬프 값 또는 기본 지속 시간.
 */
internal fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET } ?: DEFAULT_DURATION_MS
