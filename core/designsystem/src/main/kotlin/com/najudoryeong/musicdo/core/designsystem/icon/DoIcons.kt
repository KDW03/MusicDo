package com.najudoryeong.musicdo.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.najudoryeong.musicdo.core.designsystem.icon.Icon.ImageVectorIcon
import com.najudoryeong.musicdo.core.designsystem.icon.Icon.DrawableResourceIcon
import com.najudoryeong.musicdo.core.designsystem.R

object DoIcons {
    val Home = ImageVectorIcon(Icons.Rounded.Home)
    val Search = ImageVectorIcon(Icons.Rounded.Search)
    val Favorite = ImageVectorIcon(Icons.Rounded.Favorite)
    val FavoriteBorder = ImageVectorIcon(Icons.Rounded.FavoriteBorder)
    val FavoriteDrawable = DrawableResourceIcon(R.drawable.ic_favorite)
    val FavoriteBorderDrawable = DrawableResourceIcon(R.drawable.ic_favorite_border)
    val Settings = ImageVectorIcon(Icons.Rounded.Settings)
    val ArrowBack = ImageVectorIcon(Icons.Rounded.ArrowBack)
    val Clear = ImageVectorIcon(Icons.Rounded.Clear)
    val Close = ImageVectorIcon(Icons.Rounded.Close)
    val Music = DrawableResourceIcon(R.drawable.ic_music)
    val Repeat = DrawableResourceIcon(R.drawable.ic_repeat)
    val RepeatOne = DrawableResourceIcon(R.drawable.ic_repeat_one)
    val Shuffle = DrawableResourceIcon(R.drawable.ic_shuffle)
    val SkipPrevious = DrawableResourceIcon(R.drawable.ic_skip_previous)
    val Play = DrawableResourceIcon(R.drawable.ic_play)
    val Pause = DrawableResourceIcon(R.drawable.ic_pause)
    val SkipNext = DrawableResourceIcon(R.drawable.ic_skip_next)
    val Sort = DrawableResourceIcon(R.drawable.ic_sort)
    val Palette = DrawableResourceIcon(R.drawable.ic_palette)
    val DarkMode = DrawableResourceIcon(R.drawable.ic_dark_mode)
    val GitHub = DrawableResourceIcon(R.drawable.ic_github)
    val Info = ImageVectorIcon(Icons.Rounded.Info)
    val Security = DrawableResourceIcon(R.drawable.ic_security)
}

sealed interface Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon
    data class DrawableResourceIcon(@DrawableRes val resourceId: Int) : Icon
}
