

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 정렬 순서 UseCase in SettingsRepository
 */
class GetSortOrderUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.userData.map { it.sortOrder }
}
