

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import com.najudoryeong.musicdo.core.model.SortBy
import javax.inject.Inject


/**
 * 사용자 정렬 기준 set UseCase in SettingsRepository
 */
class SetSortByUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(sortBy: SortBy) = settingsRepository.setSortBy(sortBy)
}
