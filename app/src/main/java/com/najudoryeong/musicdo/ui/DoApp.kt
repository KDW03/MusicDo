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

import android.content.Context
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.najudoryeong.musicdo.R
import com.najudoryeong.musicdo.core.designsystem.componenet.DoTopAppBar
import com.najudoryeong.musicdo.navigation.DoNavHost
import com.najudoryeong.musicdo.ui.component.DoBottomBar

/**
 * MusicDo Main Composable
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun DoApp(
    onSetSystemBarsLightIcons: () -> Unit,
    onResetSystemBarsIcons: () -> Unit,
    appState: DoAppState = rememberDoAppState(),
) {
    // permission 확인
    when (appState.permissionState.status) {
        PermissionStatus.Granted -> {
            DoAppContent(
                appState = appState,
                onSetSystemBarsLightIcons = onSetSystemBarsLightIcons,
                onResetSystemBarsIcons = onResetSystemBarsIcons,
            )
        }

        is PermissionStatus.Denied -> {
            PermissionContent(
                permissionState = appState.permissionState,
                isPermissionRequested = appState.isPermissionRequested,
            )
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMotionApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
private fun DoAppContent(
    appState: DoAppState,
    onSetSystemBarsLightIcons: () -> Unit,
    onResetSystemBarsIcons: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val motionSceneContent = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        modifier = modifier.fillMaxSize(),
        motionScene = MotionScene(content = motionSceneContent),
        progress = appState.motionProgress,
    ) {
        Box(modifier = Modifier.layoutId("topBar")) {
            DoTopAppBar(
                titleResource = appState.getTitleResource(),
                shouldShowBackButton = appState.shouldShowBackButton,
                onBackClick = appState::onBackClick,
            )
        }

        Box(modifier = Modifier.layoutId("content")) {
            DoNavHost(
                navController = appState.navController,
                startDestination = appState.startDestination.route,
                onNavigateToPlayer = appState::openPlayer,
                onNavigateToArtist = appState::navigateToArtist,
                onNavigateToAlbum = appState::navigateToAlbum,
                onNavigateToFolder = appState::navigateToFolder,
            )
        }

        Box(modifier = Modifier.layoutId("miniPlayer")) {
            MiniPlayer(
                modifier = Modifier.playerSwipe(
                    swipeableState = appState.swipeableState,
                    anchors = appState.anchors,
                ),
                onNavigateToPlayer = appState::openPlayer,
            )
        }

        Box(modifier = Modifier.layoutId("fullPlayer")) {
            FullPlayer(
                modifier = Modifier.playerSwipe(
                    swipeableState = appState.swipeableState,
                    anchors = appState.anchors,
                ),
                isPlayerOpened = appState.isPlayerOpened,
                onSetSystemBarsLightIcons = onSetSystemBarsLightIcons,
                onResetSystemBarsIcons = onResetSystemBarsIcons,
                onBackClick = appState::closePlayer,
            )
        }

        Box(modifier = Modifier.layoutId("bottomBar")) {
            DoBottomBar(
                destinations = appState.topLevelDestinations,
                currentDestination = appState.currentDestination,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun Modifier.playerSwipe(
    swipeableState: SwipeableState<Int>,
    anchors: Map<Float, Int>,
) = swipeable(
    state = swipeableState,
    anchors = anchors,
    orientation = Orientation.Vertical,
    thresholds = { _, _ -> FractionalThreshold(SwipeFraction) },
)

private const val SwipeFraction = 0.3f
