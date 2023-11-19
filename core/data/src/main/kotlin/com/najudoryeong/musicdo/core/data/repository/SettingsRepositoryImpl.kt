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

import com.najudoryeong.musicdo.core.data.util.Constants
import com.najudoryeong.musicdo.core.data.util.DoVersionProvider
import com.najudoryeong.musicdo.core.datastore.PreferencesDataSource
import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import com.najudoryeong.musicdo.core.model.DarkThemeConfig
import com.najudoryeong.musicdo.core.model.PlaybackMode
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder
import com.najudoryeong.musicdo.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * core:data [SettingsRepository] interface의 구현체
 */
class SettingsRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    versionProvider: DoVersionProvider,
) : SettingsRepository {

    /**
     * [userData] Flow
     */
    override val userData: Flow<UserData> = preferencesDataSource.userData

    override val repoUrl = Constants.Urls.DO_PRIVACY_REPO_URL
    override val privacyPolicyUrl = Constants.Urls.DO_PRIVACY_POLICY_URL
    override val version = versionProvider.version

    /**
     * 사용자 정보 set 함수 using [PreferencesDataSource]
     */
    override suspend fun setPlayingQueueIds(playingQueueIds: List<String>) =
        preferencesDataSource.setPlayingQueueIds(playingQueueIds)

    override suspend fun setPlayingQueueIndex(playingQueueIndex: Int) =
        preferencesDataSource.setPlayingQueueIndex(playingQueueIndex)

    override suspend fun setPlaybackMode(playbackMode: PlaybackMode) =
        preferencesDataSource.setPlaybackMode(playbackMode)

    override suspend fun setSortOrder(sortOrder: SortOrder) =
        preferencesDataSource.setSortOrder(sortOrder)

    override suspend fun setSortBy(sortBy: SortBy) = preferencesDataSource.setSortBy(sortBy)

    override suspend fun toggleFavoriteSong(id: String, isFavorite: Boolean) =
        preferencesDataSource.toggleFavoriteSong(id, isFavorite)

    override suspend fun setDynamicColor(useDynamicColor: Boolean) =
        preferencesDataSource.setDynamicColor(useDynamicColor)

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) =
        preferencesDataSource.setDarkThemeConfig(darkThemeConfig)
}
