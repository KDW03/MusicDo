package com.najudoryeong.musicdo.core.permission

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Permission 상태
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberDoPermissionState(
    onPermissionResult: (Boolean) -> Unit = {}
) = rememberPermissionState(
    permission = DoAudioPermission,
    onPermissionResult = onPermissionResult
)

/**
 * if(Version <= TIRAMISU) READ_MEDIA_AUDIO
 * else READ_EXTERNAL_STORAGE
 */
private val DoAudioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_AUDIO
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}