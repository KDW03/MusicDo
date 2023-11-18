package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ID를 통해 앨범 조회 UseCase in MediaRepository
 */
class GetAlbumByIdUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke(albumId: Long) =
        mediaRepository.albums.map { list -> list.first { it.id == albumId } }
}




