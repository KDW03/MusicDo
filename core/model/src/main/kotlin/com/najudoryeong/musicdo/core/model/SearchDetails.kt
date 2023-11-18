package com.najudoryeong.musicdo.core.model

data class SearchDetails(
    val songs: List<Song>,
    val artists: List<Artist>,
    val albums: List<Album>,
    val folders: List<Folder>
)