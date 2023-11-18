package com.najudoryeong.musicdo.feature.search

import com.najudoryeong.musicdo.core.model.SearchDetails
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Success(
        val query: String,
        val searchDetails: SearchDetails,
        val sortOrder: SortOrder,
        val sortBy: SortBy
    ) : SearchUiState
}
