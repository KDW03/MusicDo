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
