package com.najudoryeong.musicdo.core.datastore.mapper

import com.najudoryeong.musicdo.core.datastore.DarkThemeConfigProto
import com.najudoryeong.musicdo.core.model.DarkThemeConfig

/**
 * [DarkThemeConfig] -> [DarkThemeConfigProto]
 */
internal fun DarkThemeConfig.asDarkThemeConfigProto() = when (this) {
    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
}

/**
 * [DarkThemeConfigProto] -> [DarkThemeConfig]
 */
internal fun DarkThemeConfigProto.asDarkThemeConfig() = when (this) {
    DarkThemeConfigProto.UNRECOGNIZED,
    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM -> DarkThemeConfig.FOLLOW_SYSTEM

    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
}
