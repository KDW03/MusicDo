

package com.najudoryeong.musicdo.core.domain.usecase

import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import com.najudoryeong.musicdo.core.model.SearchDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

/**
 * 미디어 검색 UseCase in MediaRepository
 * combine  Flow<List<Song>>,  Flow<List<Artist>>,  Flow<List<Album>>,  Flow<List<Folder>>
 */
class SearchMediaUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(query: String) = combine(
        mediaRepository.songs,
        mediaRepository.artists,
        mediaRepository.albums,
        mediaRepository.folders
    ) { songs, artists, albums, folders ->
        SearchDetails(songs, artists, albums, folders)
    }
        .mapLatest { searchDetails ->
            if (query.isBlank()) {
                return@mapLatest searchDetails.copy(
                    songs = emptyList(),
                    artists = emptyList(),
                    albums = emptyList()
                )
            }

            // combine 결과를 통해 각 카테고리에서 쿼리에 맞는 항목을 필터링
            val songs = searchDetails.songs.filter {
                it.title.contains(query, ignoreCase = true) ||
                    it.artist.contains(query, ignoreCase = true) ||
                    it.album.contains(query, ignoreCase = true)
            }

            val artists = searchDetails.artists.filter {
                it.name.contains(query, ignoreCase = true)
            }

            val albums = searchDetails.albums.filter {
                it.name.contains(query, ignoreCase = true) ||
                    it.artist.contains(query, ignoreCase = true)
            }

            val folders = searchDetails.folders.filter {
                it.name.contains(query, ignoreCase = true)
            }

            searchDetails.copy(
                songs = songs,
                artists = artists,
                albums = albums,
                folders = folders
            )
        }
}
