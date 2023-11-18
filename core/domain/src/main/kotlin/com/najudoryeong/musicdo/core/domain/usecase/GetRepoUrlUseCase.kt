

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * 저장소 URL 조회 UseCase in SettingsRepository
 */
class GetRepoUrlUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.repoUrl
}
