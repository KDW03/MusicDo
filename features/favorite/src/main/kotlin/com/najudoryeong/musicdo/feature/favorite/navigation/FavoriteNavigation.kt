package com.najudoryeong.musicdo.feature.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.najudoryeong.musicdo.feature.FavoriteRoute

const val FavoriteRoute = "favorite_route"

fun NavGraphBuilder.favoriteScreen(
    onNavigateToPlayer: () -> Unit
) = composable(route = FavoriteRoute) {
    FavoriteRoute(onNavigateToPlayer = onNavigateToPlayer)
}