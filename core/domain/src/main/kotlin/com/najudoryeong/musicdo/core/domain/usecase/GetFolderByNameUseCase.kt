package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 폴더 이름으로 폴더 조회 UseCase in MediaRepository
 */
class GetFolderByNameUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke(name: String) = mediaRepository.folders.map { list -> list.first { it.name == name } }
}
