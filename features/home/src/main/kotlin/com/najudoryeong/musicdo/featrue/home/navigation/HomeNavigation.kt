package com.najudoryeong.musicdo.featrue.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.najudoryeong.musicdo.featrue.home.HomeRoute


const val HomeGraphRoute = "home_graph"
const val HomeRoute = "home_route"

fun NavGraphBuilder.homeScreen(
    onNavigateToPlayer: () -> Unit,
    onNavigateToArtist: (Long) -> Unit,
    onNavigateToAlbum: (Long) -> Unit,
    onNavigateToFolder: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) = navigation(route = HomeGraphRoute, startDestination = HomeRoute) {
    composable(route = HomeRoute) {
        HomeRoute(
            onNavigateToPlayer = onNavigateToPlayer,
            onNavigateToArtist = onNavigateToArtist,
            onNavigateToAlbum = onNavigateToAlbum,
            onNavigateToFolder = onNavigateToFolder
        )
    }
    nestedGraphs()
}
