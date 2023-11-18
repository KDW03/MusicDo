package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import javax.inject.Inject


/**
 * Flow<List<Album>> Get UseCase in MediaRepository
 */
class GetAlbumsUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke() = mediaRepository.albums
}
