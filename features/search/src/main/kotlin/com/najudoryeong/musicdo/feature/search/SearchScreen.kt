package com.najudoryeong.musicdo.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SearchRoute(
    onNavigateToPlayer: () -> Unit,
    onNavigateToArtist: (Long) -> Unit,
    onNavigateToAlbum: (Long) -> Unit,
    onNavigateToFolder: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {

}