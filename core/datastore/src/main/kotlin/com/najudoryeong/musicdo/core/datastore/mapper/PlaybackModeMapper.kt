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
