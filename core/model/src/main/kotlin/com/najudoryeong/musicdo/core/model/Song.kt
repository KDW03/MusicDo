package com.najudoryeong.musicdo.core.model

import android.net.Uri
import kotlinx.datetime.LocalDateTime

data class Song(
    val mediaId: String,
    val artistId: Long,
    val albumId: Long,
    val mediaUri: Uri,
    val artworkUri: Uri,
    val title: String,
    val artist: String,
    val album: String,
    val folder: String,
    val duration: Long,
    val date: LocalDateTime,
    val isFavorite: Boolean
)
