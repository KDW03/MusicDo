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

package com.najudoryeong.musicdo.core.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.najudoryeong.musicdo.core.designsystem.componenet.DoOutlinedButton
import com.najudoryeong.musicdo.core.designsystem.componenet.DoTopAppBar
import com.najudoryeong.musicdo.core.designsystem.theme.spacing

/**
 * 권한 요청 화면 @Composable
 */
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class,
    ExperimentalLayoutApi::class,
)
@Composable
fun PermissionContent(
    permissionState: PermissionState,
    isPermissionRequested: Boolean,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DoTopAppBar(
                titleResource = R.string.app_name,
                shouldShowBackButton = false,
                onBackClick = {},
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .padding(MaterialTheme.spacing.extraMedium)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            // 권한 설명
            Text(
                text = stringResource(id = R.string.storage_access),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

            // 상세 권한 요청 설명
            Text(
                text = stringResource(id = R.string.storage_access_permission_text),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraMedium))

            // 요청 허용 버튼
            DoOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = permissionState::launchPermissionRequest,
            ) {
                Text(text = stringResource(id = R.string.grant_access))
            }

            // 요청 거부시 (설정 화면)
            AnimatedVisibility(
                visible = isPermissionRequested && !permissionState.status.shouldShowRationale,
            ) {
                DoOutlinedButton(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.smallMedium)
                        .fillMaxWidth(),
                    onClick = context::openSettings,
                ) {
                    Text(text = stringResource(id = R.string.settings))
                }
            }
        }
    }
}

/**
 * 설정 액티비티 여는 함수
 */
private fun Context.openSettings() = Intent(
    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
    Uri.fromParts("package", packageName, null),
).let(::startActivity)
