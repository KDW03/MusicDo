package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.userData
}
