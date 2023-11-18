package com.najudoryeong.musicdo.core.model

import android.net.Uri

data class Album(
    val id: Long,
    val artworkUri: Uri,
    val name: String,
    val artist: String,
    val songs: List<Song>
)
