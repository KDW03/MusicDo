/*
 * Copyright 2023 KDW03
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.najudoryeong.musicdo.core.data.repository

import com.najudoryeong.musicdo.core.datastore.PreferencesDataSource
import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import com.najudoryeong.musicdo.core.mediastore.soruce.MediaStoreDataSource
import com.najudoryeong.musicdo.core.model.Album
import com.najudoryeong.musicdo.core.model.Artist
import com.najudoryeong.musicdo.core.model.Folder
import com.najudoryeong.musicdo.core.model.Song
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * core:data [MediaRepository] interface의 구현체
 */
class MediaRepositoryImpl @Inject constructor(
    mediaStoreDataSource: MediaStoreDataSource,
    preferencesDataSource: PreferencesDataSource,
) : MediaRepository {

    /**
     * [PreferencesDataSource]의 userData를 이용해 필터링된 노래 리스트 조회
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override val songs: Flow<List<Song>> =
        preferencesDataSource.userData
            .flatMapLatest { userData ->
                mediaStoreDataSource.getSongs(
                    sortOrder = userData.sortOrder,
                    sortBy = userData.sortBy,
                    favoriteSongs = userData.favoriteSongs,
                    excludedFolders = excludedFolders,
                )
            }

    /**
     * 노래를 artistId로 그룹화하고 [Artist] 객체 리스트 생성
     */
    override val artists: Flow<List<Artist>> = songs.map { songs ->
        songs.groupBy(Song::artistId).map { (artistId, songs) ->
            val song = songs.first()
            Artist(id = artistId, name = song.artist, songs = songs)
        }
    }

    /**
     * 노래를 albumId로 그룹화하고 [Album] 객체 리스트 생성
     */
    override val albums: Flow<List<Album>> = songs.map { songs ->
        songs.groupBy(Song::albumId).map { (albumId, songs) ->
            val song = songs.first()
            Album(
                id = albumId,
                artworkUri = song.artworkUri,
                name = song.album,
                artist = song.artist,
                songs = songs,
            )
        }
    }

    /**
     * 노래를 folder name으로 그룹화하고 [Folder] 객체 리스트 생성
     */
    override val folders: Flow<List<Folder>> = songs.map { songs ->
        songs.groupBy(Song::folder).map { (name, songs) ->
            Folder(name = name, songs = songs)
        }
    }

    /**
     * 제외할 폴더 리스트
     */
    private val excludedFolders = listOf("Whatsapp Audio")
}
