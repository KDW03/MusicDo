

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
