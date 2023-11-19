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

package com.najudoryeong.musicdo.core.datastore

import androidx.datastore.core.DataStore
import com.najudoryeong.musicdo.core.datastore.mapper.asDarkThemeConfig
import com.najudoryeong.musicdo.core.datastore.mapper.asDarkThemeConfigProto
import com.najudoryeong.musicdo.core.datastore.mapper.asPlaybackMode
import com.najudoryeong.musicdo.core.datastore.mapper.asPlaybackModeProto
import com.najudoryeong.musicdo.core.datastore.mapper.asSortBy
import com.najudoryeong.musicdo.core.datastore.mapper.asSortByProto
import com.najudoryeong.musicdo.core.datastore.mapper.asSortOrder
import com.najudoryeong.musicdo.core.datastore.mapper.asSortOrderProto
import com.najudoryeong.musicdo.core.model.DarkThemeConfig
import com.najudoryeong.musicdo.core.model.PlaybackMode
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder
import com.najudoryeong.musicdo.core.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 사용자 앱 설정등 정보를 관리하는 데이터 소스 using DataStore<UserPreferences>
 */
class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    /**
     *  Protocol Buffers 데이터 Kotlin [UserData]로 변환
     */
    val userData = userPreferences.data.map { preferences ->
        UserData(
            playingQueueIds = preferences.playingQueueIdsList,
            playingQueueIndex = preferences.playingQueueIndex,
            playbackMode = preferences.playbackMode.asPlaybackMode(),
            sortOrder = preferences.sortOrder.asSortOrder(),
            sortBy = preferences.sortBy.asSortBy(),
            favoriteSongs = preferences.favoriteSongIdsMap.keys,
            darkThemeConfig = preferences.darkThemeConfig.asDarkThemeConfig(),
            useDynamicColor = preferences.useDynamicColor,
        )
    }

    /**
     * Protocol Buffers 데이터 Set
     * 필요시 변환 Mapper 확장 함수 적용
     */
    suspend fun setPlayingQueueIds(playingQueueIds: List<String>) {
        userPreferences.updateData {
            it.copy {
                this.playingQueueIds.run {
                    clear()
                    addAll(playingQueueIds)
                }
            }
        }
    }

    suspend fun setPlayingQueueIndex(playingQueueIndex: Int) {
        userPreferences.updateData {
            it.copy {
                this.playingQueueIndex = playingQueueIndex
            }
        }
    }

    suspend fun setPlaybackMode(playbackMode: PlaybackMode) {
        userPreferences.updateData {
            it.copy {
                this.playbackMode = playbackMode.asPlaybackModeProto()
            }
        }
    }

    suspend fun setSortOrder(sortOrder: SortOrder) {
        userPreferences.updateData {
            it.copy {
                this.sortOrder = sortOrder.asSortOrderProto()
            }
        }
    }

    suspend fun setSortBy(sortBy: SortBy) {
        userPreferences.updateData {
            it.copy {
                this.sortBy = sortBy.asSortByProto()
            }
        }
    }

    suspend fun toggleFavoriteSong(id: String, isFavorite: Boolean) {
        userPreferences.updateData {
            it.copy {
                if (isFavorite) {
                    favoriteSongIds.put(id, true)
                } else {
                    favoriteSongIds.remove(id)
                }
            }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = darkThemeConfig.asDarkThemeConfigProto()
            }
        }
    }

    suspend fun setDynamicColor(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }
}
