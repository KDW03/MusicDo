package com.najudoryeong.musicdo.core.model

data class UserData(
    val playingQueueIds: List<String>,
    val playingQueueIndex: Int,
    val playbackMode: PlaybackMode,
    val sortOrder: SortOrder,
    val sortBy: SortBy,

    val favoriteSongs: Set<String>,

    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean
)
