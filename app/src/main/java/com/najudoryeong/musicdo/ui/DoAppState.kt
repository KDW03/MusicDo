package com.najudoryeong.musicdo.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.najudoryeong.musicdo.navigation.TopLevelDestination
import com.najudoryeong.musicdo.navigation.util.contains
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberDoAppState(
    navController: NavHostController = rememberNavController(),
    startDestination: TopLevelDestination = TopLevelDestination.Home,
    swipeableState: SwipeableState<Int> = rememberSwipeableState(
        initialValue = 0,
        animationSpec = tween()
    ),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    density: Density = LocalDensity.current,
    configuration: Configuration = LocalConfiguration.current
): DoAppState {
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    val swipeAreaHeight = screenHeight - SwipeAreaOffset

    return remember(
        navController,
        startDestination,
        swipeableState,
        swipeAreaHeight,
        coroutineScope
    ) {
        DoAppState(
            navController = navController,
            startDestination = startDestination,
            swipeableState = swipeableState,
            swipeAreaHeight = swipeAreaHeight,
            coroutineScope = coroutineScope
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Stable
class DoAppState(
    val navController: NavHostController,
    val startDestination: TopLevelDestination,
    val swipeableState: SwipeableState<Int>,
    val swipeAreaHeight: Float,
    val coroutineScope: CoroutineScope
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    private val currentTopLevelDestination: TopLevelDestination
        @Composable get() {
            topLevelDestinations.firstOrNull { it.route in currentDestination }
                ?.let { _currentTopLevelDestination = it }
            return _currentTopLevelDestination
        }

    val isTopLevelDestination: Boolean
        @Composable get() = currentTopLevelDestination.route in currentDestination

    val shouldShowBackButton: Boolean
        @Composable get() = currentDestination != null && topLevelRoutes.none { it in currentDestination }

    val topLevelDestinations = TopLevelDestination.values()

    private var _currentTopLevelDestination by mutableStateOf(startDestination)
    private val topLevelRoutes = listOf(HomeRoute, SearchRoute, FavoriteRoute, SettingsRoute)

    val anchors = mapOf(0f to 0, -swipeAreaHeight to 1)
    private val swipeProgress @Composable get() = swipeableState.offset.value / -swipeAreaHeight
    val motionProgress @Composable get() = max(0f, min(swipeProgress, 1f))
    val isPlayerOpened @Composable get() = swipeableState.currentValue == 1

    @OptIn(ExperimentalPermissionsApi::class)
    val permissionState: PermissionState
        @Composable get() = rememberDoPermissionState { isPermissionRequested = true }

    var isPermissionRequested by mutableStateOf(false)
        private set

    fun navigateToTopLevelDestination(destination: TopLevelDestination) =
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    fun openPlayer() = coroutineScope.launch { swipeableState.animateTo(1) }
    fun closePlayer() = coroutineScope.launch { swipeableState.animateTo(0) }

    fun navigateToArtist(prefix: String, artistId: Long) = navController.navigateToLibrary(
        prefix = prefix,
        libraryType = LibraryType.Artist,
        libraryId = artistId.toString()
    )

    fun navigateToAlbum(prefix: String, albumId: Long) = navController.navigateToLibrary(
        prefix = prefix,
        libraryType = LibraryType.Album,
        libraryId = albumId.toString()
    )

    fun navigateToFolder(prefix: String, name: String) = navController.navigateToLibrary(
        prefix = prefix,
        libraryType = LibraryType.Folder,
        libraryId = name
    )

    @Suppress("TopLevelComposableFunctions")
    @StringRes
    @Composable
    fun getTitleResource(): Int {
        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        return when (currentBackStackEntry?.destination?.route) {
            HomeRoute -> homeR.string.home
            SearchRoute -> searchR.string.search
            FavoriteRoute -> favoriteR.string.favorite
            SettingsRoute -> settingsR.string.settings
            LibraryRouteWithArguments -> currentBackStackEntry.getLibraryType().getTitleResource()
            else -> currentTopLevelDestination.titleResource
        }
    }

    fun onBackClick() = navController.popBackStack()
}

private const val SwipeAreaOffset = 400
