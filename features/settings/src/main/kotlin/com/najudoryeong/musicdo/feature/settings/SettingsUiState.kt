package com.najudoryeong.musicdo.feature.settings

import android.net.Uri
import com.najudoryeong.musicdo.core.model.DarkThemeConfig

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(
        val supportsDynamicTheming: Boolean,
        val useDynamicColor: Boolean,
        val darkThemeConfig: DarkThemeConfig,
        val repoUrl: Uri,
        val privacyPolicyUrl: Uri,
        val version: String,
    ) : SettingsUiState
}