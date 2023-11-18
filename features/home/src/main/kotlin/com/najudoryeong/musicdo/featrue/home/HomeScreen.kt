package com.najudoryeong.musicdo.featrue.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HomeRoute(
    onNavigateToPlayer: () -> Unit,
    onNavigateToArtist: (Long) -> Unit,
    onNavigateToAlbum: (Long) -> Unit,
    onNavigateToFolder: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

}