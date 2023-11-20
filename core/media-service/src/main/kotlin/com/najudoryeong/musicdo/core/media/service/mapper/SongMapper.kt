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

package com.najudoryeong.musicdo.core.media.service.mapper

import com.najudoryeong.musicdo.core.media.service.utiil.buildPlayableMediaItem
import com.najudoryeong.musicdo.core.model.Song

/**
 * Song 객체를 미디어 플레이어에 의해 재생될 수 있는 형식의 객체인 PlayableMediaItem으로 변환하는 확장 함수
 */
internal fun Song.asMediaItem() = buildPlayableMediaItem(
    mediaId = mediaId,
    artistId = artistId,
    albumId = albumId,
    mediaUri = mediaUri,
    artworkUri = artworkUri,
    title = title,
    artist = artist,
    folder = folder,
    duration = duration,
    date = date,
    isFavorite = isFavorite,
)
