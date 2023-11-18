

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * 동적 색상 여부 set UseCase in SettingsRepository
 */
class SetDynamicColorUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(useDynamicColor: Boolean) =
        settingsRepository.setDynamicColor(useDynamicColor)
}
