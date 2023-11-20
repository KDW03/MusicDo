package com.najudoryeong.musicdo.core.model

import com.najudoryeong.musicdo.core.media.common.MediaConstants.DEFAULT_DURATION_MS
import com.najudoryeong.musicdo.core.media.common.MediaConstants.DEFAULT_INDEX
import com.najudoryeong.musicdo.core.media.common.MediaConstants.DEFAULT_MEDIA_ID

/**
 * 노래 상태
 */
data class MusicState(
    val currentMediaId: String = DEFAULT_MEDIA_ID,
    val currentSongIndex: Int = DEFAULT_INDEX,
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = DEFAULT_DURATION_MS
)
