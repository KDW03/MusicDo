package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import javax.inject.Inject

/**
 * Flow<List<Folder>> Get UseCase in MediaRepository
 */
class GetFoldersUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke() = mediaRepository.folders
}