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

package com.najudoryeong.musicdo.core.media.notification.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.najudoryeong.musicdo.core.media.notification.R

/**
 * Uri 객체를 비트맵 이미지로 변환하는 확장 함수
 * 주어진 Uri에서 이미지를 로드하고 Bitmap 객체로 반환
 * 이미지 로딩에 실패하면 기본 음악 커버 이미지를 반환
 */
internal suspend fun Uri.asArtworkBitmap(context: Context): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .placeholder(R.drawable.music_cover)
        .error(R.drawable.music_cover)
        .build()

    val drawable = loader.execute(request).drawable
    return drawable?.toBitmap()
}
