package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ID를 통해 앨범 조회 UseCase in MediaRepository
 */
class GetArtistByIdUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke(artistId: Long) =
        mediaRepository.artists.map { list -> list.first { it.id == artistId } }
}
