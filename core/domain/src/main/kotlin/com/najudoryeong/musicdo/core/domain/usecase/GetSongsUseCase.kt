

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import javax.inject.Inject

/**
 * 전체 노래 리스트 조회 UseCase in MediaRepository
 */
class GetSongsUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke() = mediaRepository.songs
}
