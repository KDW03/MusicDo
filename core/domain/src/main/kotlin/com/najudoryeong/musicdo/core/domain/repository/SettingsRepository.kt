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

/**
 * interface for SettingRepository
 */
interface SettingsRepository {

    val userData: Flow<UserData>

    val repoUrl: String
    val privacyPolicyUrl: String
    val version: String

    /** 재생 큐 ID set 함수 */
    suspend fun setPlayingQueueIds(playingQueueIds: List<String>)
    /** 재생 큐 Index set 함수 */
    suspend fun setPlayingQueueIndex(playingQueueIndex: Int)
    /** 재생 모드 set 함수 */
    suspend fun setPlaybackMode(playbackMode: PlaybackMode)
    /** 정렬 순서 set 함수 */
    suspend fun setSortOrder(sortOrder: SortOrder)
    /** 정렬 기준 set 함수 */
    suspend fun setSortBy(sortBy: SortBy)
    /** 특정 곡 즐겨 찾기 toggle 함수 */
    suspend fun toggleFavoriteSong(id: String, isFavorite: Boolean)
    /** 동적 색상 여부 set 함수 */
    suspend fun setDynamicColor(useDynamicColor: Boolean)
    /** 다크 테마 여부 set 함수 */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
}
