package com.najudoryeong.musicdo.core.mediastore.util

import android.net.Uri
import android.os.Build
import android.provider.MediaStore

/**
 * 미디어 데이터 구성
 */
internal object MediaStoreConfig {
    object Song {
        // 노래 컬렉션에 대한 Uri
        // V >= Android 10
        val Collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            // V < Android 10
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        val Projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )
    }
}