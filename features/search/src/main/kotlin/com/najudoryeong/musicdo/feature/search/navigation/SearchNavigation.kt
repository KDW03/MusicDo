package com.najudoryeong.musicdo.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


const val SearchGraphRoute = "search_graph"
const val SearchRoute = "search_route"
fun NavGraphBuilder.searchScreen(
    onNavigateToPlayer: () -> Unit,
    onNavigateToArtist: (Long) -> Unit,
    onNavigateToAlbum: (Long) -> Unit,
    onNavigateToFolder: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) = navigation(route = SearchGraphRoute, startDestination = SearchRoute) {
    composable(route = SearchRoute) {
        SearchRoute(
            onNavigateToPlayer = onNavigateToPlayer,
            onNavigateToArtist = onNavigateToArtist,
            onNavigateToAlbum = onNavigateToAlbum,
            onNavigateToFolder = onNavigateToFolder
        )
    }
    nestedGraphs()
}