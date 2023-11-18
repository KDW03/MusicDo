

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * 사용자 재생 큐 인덱스 set UseCase in SettingsRepository
 */
class SetPlayingQueueIndexUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(index: Int) = settingsRepository.setPlayingQueueIndex(index)
}
