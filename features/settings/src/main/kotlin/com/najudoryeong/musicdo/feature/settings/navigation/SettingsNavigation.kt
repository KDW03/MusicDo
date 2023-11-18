package com.najudoryeong.musicdo.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.najudoryeong.musicdo.feature.settings.SettingsRoute

const val SettingsRoute = "settings_route"

fun NavGraphBuilder.settingsScreen() = composable(route = SettingsRoute) { SettingsRoute() }
