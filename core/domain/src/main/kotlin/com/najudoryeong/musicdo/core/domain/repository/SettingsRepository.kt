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

package com.najudoryeong.musicdo.core.domain.repository

import com.najudoryeong.musicdo.core.model.DarkThemeConfig
import com.najudoryeong.musicdo.core.model.PlaybackMode
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder
import com.najudoryeong.musicdo.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val userData: Flow<UserData>

    val repoUrl: String
    val privacyPolicyUrl: String
    val version: String

    suspend fun setPlayingQueueIds(playingQueueIds: List<String>)
    suspend fun setPlayingQueueIndex(playingQueueIndex: Int)
    suspend fun setPlaybackMode(playbackMode: PlaybackMode)
    suspend fun setSortOrder(sortOrder: SortOrder)
    suspend fun setSortBy(sortBy: SortBy)
    suspend fun toggleFavoriteSong(id: String, isFavorite: Boolean)
    suspend fun setDynamicColor(useDynamicColor: Boolean)
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
}
