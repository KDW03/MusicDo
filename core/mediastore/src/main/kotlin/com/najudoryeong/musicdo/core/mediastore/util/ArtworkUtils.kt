package com.najudoryeong.musicdo.core.mediastore.util

import android.content.ContentUris
import android.net.Uri

/**
 * 앨범 ID를 받아 해당 앨범의 아트워크 URI를 반환하는 확장 함수
 */
internal fun Long.asArtworkUri() = ContentUris.withAppendedId(Uri.parse(ALBUM_ART_URI), this)

private const val ALBUM_ART_URI = "content://media/external/audio/albumart"
