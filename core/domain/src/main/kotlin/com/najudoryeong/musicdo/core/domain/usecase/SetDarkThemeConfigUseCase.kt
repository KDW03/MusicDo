

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import com.najudoryeong.musicdo.core.model.DarkThemeConfig
import javax.inject.Inject

/**
 * 다크 테마 여부 set UseCase in SettingsRepository
 */
class SetDarkThemeConfigUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(darkThemeConfig: DarkThemeConfig) =
        settingsRepository.setDarkThemeConfig(darkThemeConfig)
}
