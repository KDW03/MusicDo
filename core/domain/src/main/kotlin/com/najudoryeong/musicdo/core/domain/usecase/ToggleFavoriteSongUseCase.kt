

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * 사용자 특정 노래 즐겨찾기 toggle UseCase in SettingsRepository
 */
class ToggleFavoriteSongUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(id: String, isFavorite: Boolean) =
        settingsRepository.toggleFavoriteSong(id, isFavorite)
}
