package com.najudoryeong.musicdo.navigation

import androidx.annotation.StringRes
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.icon.Icon.ImageVectorIcon
import com.najudoryeong.musicdo.featrue.home.navigation.HomeGraphRoute
import com.najudoryeong.musicdo.feature.favorite.navigation.FavoriteRoute
import com.najudoryeong.musicdo.feature.playlist.navigation.PlaylistRoute
import com.najudoryeong.musicdo.feature.search.navigation.SearchGraphRoute
import com.najudoryeong.musicdo.feature.settings.navigation.SettingsRoute

enum class TopLevelDestination(
    val route: String,
    val icon: ImageVectorIcon,
    @StringRes val titleResource: Int
) {
    Home(
        route = HomeGraphRoute,
        icon = DoIcons.Home,
        titleResource = homeR.string.home
    ),
    Search(
        route = SearchGraphRoute,
        icon = DoIcons.Search,
        titleResource = searchR.string.search
    ),
    Favorite(
        route = FavoriteRoute,
        icon = DoIcons.Favorite,
        titleResource = favoriteR.string.favorite
    ),
    Playlist(
        route = PlaylistRoute,
        icon = DoIcons.Playlist,
        titleResource = favoriteR.string.favorite
    ),
    Settings(
        route = SettingsRoute,
        icon = DoIcons.Settings,
        titleResource = settingsR.string.settings
    )
}
