package com.najudoryeong.musicdo.core.media.service.utiil

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

/**
 * PlayableMediaItem을 구축하는 함수.
 *
 * @param mediaId 미디어 고유 ID.
 * @param artistId 아티스트 고유 ID.
 * @param albumId 앨범 고유 ID.
 * @param mediaUri 미디어 URI.
 * @param artworkUri 아트워크 URI.
 * @param title 곡 제목.
 * @param artist 아티스트 이름.
 * @param folder 폴더 정보.
 * @param duration 곡 재생 시간(밀리초).
 * @param date 곡이 추가된 날짜.
 * @param isFavorite 즐겨찾기 여부.
 * @return MediaItem - 구축된 미디어 아이템.
 */
internal fun buildPlayableMediaItem(
    mediaId: String,
    artistId: Long,
    albumId: Long,
    mediaUri: Uri,
    artworkUri: Uri,
    title: String,
    artist: String,
    folder: String,
    duration: Long,
    date: LocalDateTime,
    isFavorite: Boolean
) = MediaItem.Builder()
    .setMediaId(mediaId)
    .setRequestMetadata(
        MediaItem.RequestMetadata.Builder()
            .setMediaUri(mediaUri)
            .build()
    )
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setArtworkUri(artworkUri)
            .setTitle(title)
            .setArtist(artist)
            .setIsBrowsable(false)
            .setIsPlayable(true)
            .setExtras(
                bundleOf(
                    ARTIST_ID to artistId,
                    ALBUM_ID to albumId,
                    FOLDER to folder,
                    DURATION to duration,
                    DATE to date.toInstant(TimeZone.currentSystemDefault()).epochSeconds,
                    IS_FAVORITE_ID to isFavorite
                )
            )
            .build()
    )
    .build()

internal const val ARTIST_ID = "artist_id"
internal const val ALBUM_ID = "album_id"
internal const val FOLDER = "folder"
internal const val DURATION = "duration"
internal const val DATE = "date"
internal const val IS_FAVORITE_ID = "favorite_id"
