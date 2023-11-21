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

package com.najudoryeong.musicdo.feature.player.util

import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation
import com.google.android.renderscript.Toolkit

/**
 * 비트맵 이미지에 대한 흐림(blur) 효과를 적용하는 클래스
 */
class BlurTransformation(private val radius: Int = DefaultRadius) : Transformation {
    override val cacheKey = "${javaClass.name}-$radius"

    override suspend fun transform(input: Bitmap, size: Size) =
        Toolkit.blur(inputBitmap = input, radius = radius)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is BlurTransformation && radius == other.radius
    }

    override fun hashCode() = radius.hashCode()

    // 기본 흐림 정도
    private companion object {
        private const val DefaultRadius = 20
    }
}
