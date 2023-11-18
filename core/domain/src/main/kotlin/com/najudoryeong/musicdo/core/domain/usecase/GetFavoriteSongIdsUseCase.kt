package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 즐겨찾기 노래 리스트 조회 UseCase in SettingsRepository
 */
class GetFavoriteSongIdsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.userData.map { it.favoriteSongs }
}
