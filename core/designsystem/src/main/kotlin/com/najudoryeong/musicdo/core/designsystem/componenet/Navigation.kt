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

package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.designsystem.theme.DoTheme

@Composable
fun RowScope.DoNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DoNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = DoNavigationDefaults.navigationContentColor(),
            selectedTextColor = DoNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = DoNavigationDefaults.navigationContentColor(),
            indicatorColor = DoNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@ThemePreviews
@Composable
fun DoNavigationPreview() {
    val items = listOf("oneB", "twoB", "threeB")
    val icons = listOf(
        DoIcons.Home,
        DoIcons.Home,
        DoIcons.Home,
    )
    val selectedIcons = listOf(
        DoIcons.Home,
        DoIcons.Home,
        DoIcons.Home,
    )

    DoTheme {
        DoNavigationBar {
            items.forEachIndexed { index, item ->
                DoNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index].imageVector,
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index].imageVector,
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

@Composable
fun DoNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = DoNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

object DoNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
