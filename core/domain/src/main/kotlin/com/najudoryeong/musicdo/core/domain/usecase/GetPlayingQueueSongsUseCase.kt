

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 재생 큐에 있는 아이디를 통해 전체 노래 목록에 있는 노래 조회 combine UseCase in MediaRepository, SettingsRepository
 */
class GetPlayingQueueSongsUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = combine(
        mediaRepository.songs,
        settingsRepository.userData.map { it.playingQueueIds }
    ) { songs, playingQueueIds ->
        playingQueueIds.mapNotNull { playingQueueId -> songs.find { it.mediaId == playingQueueId } }
    }
}
