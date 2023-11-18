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

package com.najudoryeong.musicdo.core.mediastore.util

import android.content.ContentUris
import android.net.Uri

/**
 * 앨범 ID를 받아 해당 앨범의 아트워크 URI를 반환하는 확장 함수
 */
internal fun Long.asArtworkUri() = ContentUris.withAppendedId(Uri.parse(ALBUM_ART_URI), this)

private const val ALBUM_ART_URI = "content://media/external/audio/albumart"
