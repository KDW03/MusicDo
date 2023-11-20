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

package com.najudoryeong.musicdo.navigation

import androidx.annotation.StringRes
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.icon.Icon.ImageVectorIcon
import com.najudoryeong.musicdo.featrue.home.navigation.HomeGraphRoute
import com.najudoryeong.musicdo.feature.favorite.navigation.FavoriteRoute
import com.najudoryeong.musicdo.feature.playlist.navigation.PlaylistRoute
import com.najudoryeong.musicdo.feature.search.navigation.SearchGraphRoute
import com.najudoryeong.musicdo.feature.settings.navigation.SettingsRoute
import com.najudoryeong.musicdo.feature.favorite.R as favoriteR
import com.najudoryeong.musicdo.feature.home.R as homeR
import com.najudoryeong.musicdo.feature.search.R as searchR
import com.najudoryeong.musicdo.feature.settings.R as settingsR
enum class TopLevelDestination(
    val route: String,
    val icon: ImageVectorIcon,
    @StringRes val titleResource: Int,
) {
    Home(
        route = HomeGraphRoute,
        icon = DoIcons.Home,
        titleResource = homeR.string.home,
    ),
    Search(
        route = SearchGraphRoute,
        icon = DoIcons.Search,
        titleResource = searchR.string.search,
    ),
    Favorite(
        route = FavoriteRoute,
        icon = DoIcons.Favorite,
        titleResource = favoriteR.string.favorite,
    ),
    Playlist(
        route = PlaylistRoute,
        icon = DoIcons.Playlist,
        titleResource = favoriteR.string.favorite,
    ),
    Settings(
        route = SettingsRoute,
        icon = DoIcons.Settings,
        titleResource = settingsR.string.settings,
    ),
}
