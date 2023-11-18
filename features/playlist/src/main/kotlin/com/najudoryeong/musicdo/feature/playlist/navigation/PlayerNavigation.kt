package com.najudoryeong.musicdo.feature.playlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.najudoryeong.musicdo.feature.playlist.PlaylistRoute

const val PlaylistGraphRoute = "playlist_graph"
const val PlaylistRoute = "playlist_route"

fun NavGraphBuilder.playerScreen(

) = navigation(route = PlaylistGraphRoute, startDestination = PlaylistRoute) {
    composable(route = PlaylistRoute) {
        PlaylistRoute()
    }
}


