package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 재생 큐 인덱스 조회 UseCase in SettingsRepository
 */
class GetPlayingQueueIndexUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.userData.map { it.playingQueueIndex }
}
