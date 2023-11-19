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

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import com.najudoryeong.musicdo.core.model.PlaybackMode
import javax.inject.Inject

/**
 * 재생 모드 set UseCase in SettingsRepository
 */
class SetPlaybackModeUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(playbackMode: PlaybackMode) =
        settingsRepository.setPlaybackMode(playbackMode)
}
