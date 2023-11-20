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
