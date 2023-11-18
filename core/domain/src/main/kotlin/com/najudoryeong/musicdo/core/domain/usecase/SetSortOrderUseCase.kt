

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import com.najudoryeong.musicdo.core.model.SortOrder
import javax.inject.Inject

/**
 * 사용자 정렬 순서 set UseCase in SettingsRepository
 */
class SetSortOrderUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(sortOrder: SortOrder) = settingsRepository.setSortOrder(sortOrder)
}
