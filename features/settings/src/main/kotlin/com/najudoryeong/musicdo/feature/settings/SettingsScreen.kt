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

package com.najudoryeong.musicdo.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.najudoryeong.musicdo.core.designsystem.componenet.RadioButtonText
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.DarkThemeConfig
import com.najudoryeong.musicdo.core.model.DarkThemeConfig.DARK
import com.najudoryeong.musicdo.core.model.DarkThemeConfig.FOLLOW_SYSTEM
import com.najudoryeong.musicdo.core.model.DarkThemeConfig.LIGHT
import com.najudoryeong.musicdo.feature.settings.componenet.InfoText
import com.najudoryeong.musicdo.feature.settings.componenet.RadioButtonGroup
import com.najudoryeong.musicdo.feature.settings.componenet.UrlText
import com.najudoryeong.musicdo.feature.settings.componenet.group

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        uiState = uiState,
        onChangeDynamicColor = viewModel::setDynamicColor,
        onChangeDarkThemeConfig = viewModel::setDarkThemeConfig,
    )
}

@Composable
private fun SettingsScreen(
    uiState: SettingsUiState,
    onChangeDynamicColor: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        SettingsUiState.Loading -> Unit

        is SettingsUiState.Success -> {
            SettingsScreenContent(
                uiState = uiState,
                onChangeDynamicColor = onChangeDynamicColor,
                onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun SettingsScreenContent(
    uiState: SettingsUiState.Success,
    onChangeDynamicColor: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.smallMedium),
    ) {
        group(titleResource = R.string.appearance) {
            if (uiState.supportsDynamicTheming) {
                RadioButtonGroup(
                    icon = DoIcons.Palette,
                    titleRes = R.string.use_dynamic_color,
                ) {
                    RadioButtonText(
                        textRes = R.string.yes,
                        isSelected = uiState.useDynamicColor,
                        onClick = { onChangeDynamicColor(true) },
                    )
                    RadioButtonText(
                        textRes = R.string.no,
                        isSelected = !uiState.useDynamicColor,
                        onClick = { onChangeDynamicColor(false) },
                    )
                }
            }

            RadioButtonGroup(
                icon = DoIcons.DarkMode,
                titleRes = R.string.dark_mode_preference,
            ) {
                RadioButtonText(
                    textRes = R.string.system_default,
                    isSelected = uiState.darkThemeConfig == FOLLOW_SYSTEM,
                    onClick = { onChangeDarkThemeConfig(FOLLOW_SYSTEM) },
                )
                RadioButtonText(
                    textRes = R.string.light,
                    isSelected = uiState.darkThemeConfig == LIGHT,
                    onClick = { onChangeDarkThemeConfig(LIGHT) },
                )
                RadioButtonText(
                    textRes = R.string.dark,
                    isSelected = uiState.darkThemeConfig == DARK,
                    onClick = { onChangeDarkThemeConfig(DARK) },
                )
            }
        }

        group(titleResource = R.string.about) {
            UrlText(
                icon = DoIcons.GitHub,
                textResource = R.string.source_code_github,
                url = uiState.repoUrl,
            )
            UrlText(
                icon = DoIcons.Security,
                textResource = R.string.privacy_policy,
                url = uiState.privacyPolicyUrl,
            )
            InfoText(
                icon = DoIcons.Info,
                textResource = R.string.version,
                info = uiState.version,
            )
        }
    }
}
