

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * 사용자 재생 큐에 노래 아이디 리스트 set UseCase in SettingsRepository
 */
class SetPlayingQueueIdsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(playingQueueIds: List<String>) =
        settingsRepository.setPlayingQueueIds(playingQueueIds)
}
