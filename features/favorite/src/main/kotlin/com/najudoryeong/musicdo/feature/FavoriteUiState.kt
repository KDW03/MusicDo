package com.najudoryeong.musicdo.feature

import com.najudoryeong.musicdo.core.model.Song
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder

sealed interface FavoriteUiState {
    data object Loading : FavoriteUiState

    data class Success(
        val songs: List<Song>,
        val sortOrder: SortOrder,
        val sortBy: SortBy
    ) : FavoriteUiState
}
