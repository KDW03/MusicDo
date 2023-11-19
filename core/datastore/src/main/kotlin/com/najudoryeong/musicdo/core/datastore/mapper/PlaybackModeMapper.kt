package com.najudoryeong.musicdo.core.datastore.mapper

import com.najudoryeong.musicdo.core.datastore.PlaybackModeProto
import com.najudoryeong.musicdo.core.model.PlaybackMode

/**
 * [PlaybackMode] to [PlaybackModeProto]
 */
internal fun PlaybackMode.asPlaybackModeProto() = when (this) {
    PlaybackMode.REPEAT -> PlaybackModeProto.PLAYBACK_MODE_REPEAT
    PlaybackMode.REPEAT_ONE -> PlaybackModeProto.PLAYBACK_MODE_REPEAT_ONE
    PlaybackMode.SHUFFLE -> PlaybackModeProto.PLAYBACK_MODE_SHUFFLE
}

/**
 * [PlaybackModeProto] to [PlaybackMode]
 */
internal fun PlaybackModeProto.asPlaybackMode() = when (this) {
    PlaybackModeProto.UNRECOGNIZED, PlaybackModeProto.PLAYBACK_MODE_REPEAT -> PlaybackMode.REPEAT
    PlaybackModeProto.PLAYBACK_MODE_REPEAT_ONE -> PlaybackMode.REPEAT_ONE
    PlaybackModeProto.PLAYBACK_MODE_SHUFFLE -> PlaybackMode.SHUFFLE
}
