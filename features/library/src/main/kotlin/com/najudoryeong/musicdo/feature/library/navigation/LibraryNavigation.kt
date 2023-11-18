package com.najudoryeong.musicdo.feature.library.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.najudoryeong.musicdo.feature.library.LibraryRoute
import com.najudoryeong.musicdo.feature.library.model.LibraryType

private const val LibraryRoute = "library_route"

private const val LibraryTypeArg = "library_type"
private const val LibraryIdArg = "library_id"

const val LibraryRouteWithArguments = "$LibraryRoute/{$LibraryTypeArg}/{$LibraryIdArg}"

/**
 * LibraryType 및 ID 추출
 */
fun NavBackStackEntry.getLibraryType() =
    LibraryType[checkNotNull(arguments?.getString(LibraryTypeArg))]

internal fun SavedStateHandle.getLibraryType() = LibraryType[checkNotNull(this[LibraryTypeArg])]
internal fun SavedStateHandle.getLibraryId(): String = checkNotNull(this[LibraryIdArg])

fun NavController.navigateToLibrary(
    prefix: String,
    libraryType: LibraryType,
    libraryId: String
) = navigate(route = "${prefix}_$LibraryRoute/${libraryType.value}/$libraryId") {
    launchSingleTop = true
}

fun NavGraphBuilder.libraryScreen(
    prefix: String,
    onNavigateToPlayer: () -> Unit
) = composable(
    route = "${prefix}_$LibraryRouteWithArguments",
    arguments = listOf(
        navArgument(LibraryTypeArg) { type = NavType.StringType },
        navArgument(LibraryIdArg) { type = NavType.StringType }
    )
) {
    LibraryRoute(onNavigateToPlayer = onNavigateToPlayer)
}