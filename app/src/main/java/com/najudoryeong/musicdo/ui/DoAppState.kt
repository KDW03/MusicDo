/*
 * Copyright 2023 KDW03
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.najudoryeong.musicdo.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
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
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.najudoryeong.musicdo.core.permission.rememberDoPermissionState
import com.najudoryeong.musicdo.featrue.home.navigation.HomeRoute
import com.najudoryeong.musicdo.feature.favorite.navigation.FavoriteRoute
import com.najudoryeong.musicdo.feature.library.model.LibraryType
import com.najudoryeong.musicdo.feature.library.navigation.LibraryRouteWithArguments
import com.najudoryeong.musicdo.feature.library.navigation.getLibraryType
import com.najudoryeong.musicdo.feature.library.navigation.navigateToLibrary
import com.najudoryeong.musicdo.feature.library.util.getTitleResource
import com.najudoryeong.musicdo.feature.search.navigation.SearchRoute
import com.najudoryeong.musicdo.feature.settings.navigation.SettingsRoute
import com.najudoryeong.musicdo.navigation.TopLevelDestination
import com.najudoryeong.musicdo.navigation.util.contains
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import com.najudoryeong.musicdo.feature.favorite.R as favoriteR
import com.najudoryeong.musicdo.feature.home.R as homeR
import com.najudoryeong.musicdo.feature.search.R as searchR
import com.najudoryeong.musicdo.feature.settings.R as settingsR

/**
 * 네비게이션 및 앱 전반적인 설정 상태
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberDoAppState(
    navController: NavHostController = rememberNavController(),
    startDestination: TopLevelDestination = TopLevelDestination.Home,
    swipeableState: SwipeableState<Int> = rememberSwipeableState(
        initialValue = 0,
        animationSpec = tween(),
    ),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    density: Density = LocalDensity.current,
    configuration: Configuration = LocalConfiguration.current,
): DoAppState {
    // 화면 높이 계산 및 스와이프 가능 영역 높이 계산
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    val swipeAreaHeight = screenHeight - SwipeAreaOffset

    return remember(
        navController,
        startDestination,
        swipeableState,
        swipeAreaHeight,
        coroutineScope,
    ) {
        DoAppState(
            navController = navController,
            startDestination = startDestination,
            swipeableState = swipeableState,
            swipeAreaHeight = swipeAreaHeight,
            coroutineScope = coroutineScope,
        )
    }
}

/**
 * 네비게이션 및 앱 전반적인 상태를 캡슐화
 */
@OptIn(ExperimentalMaterialApi::class)
@Stable
class DoAppState(
    val navController: NavHostController,
    val startDestination: TopLevelDestination,
    val swipeableState: SwipeableState<Int>, // using in player
    val swipeAreaHeight: Float,
    val coroutineScope: CoroutineScope,
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

    // 스와이프 앵커 지점 정의
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

    // 플레이어 Full Mini 전환
    fun openPlayer() = coroutineScope.launch { swipeableState.animateTo(1) }
    fun closePlayer() = coroutineScope.launch { swipeableState.animateTo(0) }

    fun navigateToArtist(prefix: String, artistId: Long) = navController.navigateToLibrary(
        prefix = prefix,
        libraryType = LibraryType.Artist,
        libraryId = artistId.toString(),
    )

    fun navigateToAlbum(prefix: String, albumId: Long) = navController.navigateToLibrary(
        prefix = prefix,
        libraryType = LibraryType.Album,
        libraryId = albumId.toString(),
    )

    fun navigateToFolder(prefix: String, name: String) = navController.navigateToLibrary(
        prefix = prefix,
        libraryType = LibraryType.Folder,
        libraryId = name,
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

/**
 * screenHeight - 400dp 이상으로 스크롤시 [FullPlayer] ui 노출
 */
private const val SwipeAreaOffset = 400
