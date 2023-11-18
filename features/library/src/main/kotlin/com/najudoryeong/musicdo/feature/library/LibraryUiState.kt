package com.najudoryeong.musicdo.feature.library

import com.najudoryeong.musicdo.core.model.Album
import com.najudoryeong.musicdo.core.model.Artist
import com.najudoryeong.musicdo.core.model.Folder
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder

internal sealed interface LibraryUiState {
    data object Loading : LibraryUiState

    data class ArtistType(
        val artist: Artist,
        val sortOrder: SortOrder,
        val sortBy: SortBy,
    ) : LibraryUiState

    data class AlbumType(
        val album: Album,
        val sortOrder: SortOrder,
        val sortBy: SortBy,
    ) : LibraryUiState

    data class FolderType(
        val folder: Folder,
        val sortOrder: SortOrder,
        val sortBy: SortBy,
    ) : LibraryUiState
}
