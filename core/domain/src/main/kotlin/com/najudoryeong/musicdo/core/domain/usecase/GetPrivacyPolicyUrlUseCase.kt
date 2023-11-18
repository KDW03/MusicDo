

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * 개인정보 처리방침 URL UseCase in SettingsRepository
 */
class GetPrivacyPolicyUrlUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.privacyPolicyUrl
}
