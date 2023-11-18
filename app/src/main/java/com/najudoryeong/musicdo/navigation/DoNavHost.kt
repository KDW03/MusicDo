package com.najudoryeong.musicdo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun DoNavHost(
    navController: NavHostController,
    startDestination: String,
    onNavigateToPlayer: () -> Unit,
    onNavigateToArtist: (prefix: String, artistId: Long) -> Unit,
    onNavigateToAlbum: (prefix: String, albumId: Long) -> Unit,
    onNavigateToFolder: (prefix: String, name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            onNavigateToPlayer = onNavigateToPlayer,
            onNavigateToArtist = { artistId -> onNavigateToArtist(HomeGraphRoute, artistId) },
            onNavigateToAlbum = { albumId -> onNavigateToAlbum(HomeGraphRoute, albumId) },
            onNavigateToFolder = { name -> onNavigateToFolder(HomeGraphRoute, name) }
        ) {
            libraryScreen(prefix = HomeGraphRoute, onNavigateToPlayer = onNavigateToPlayer)
        }
        searchScreen(
            onNavigateToPlayer = onNavigateToPlayer,
            onNavigateToArtist = { artistId -> onNavigateToArtist(SearchGraphRoute, artistId) },
            onNavigateToAlbum = { albumId -> onNavigateToAlbum(SearchGraphRoute, albumId) },
            onNavigateToFolder = { name -> onNavigateToFolder(SearchGraphRoute, name) }
        ) {
            libraryScreen(prefix = SearchGraphRoute, onNavigateToPlayer = onNavigateToPlayer)
        }
        favoriteScreen(onNavigateToPlayer = onNavigateToPlayer)
        playlistScreen()
        settingsScreen()
    }
}