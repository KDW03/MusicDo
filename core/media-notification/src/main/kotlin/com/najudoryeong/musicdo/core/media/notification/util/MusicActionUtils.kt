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

package com.najudoryeong.musicdo.core.media.notification.util

import android.content.Context
import androidx.core.graphics.drawable.IconCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import com.najudoryeong.musicdo.core.media.notification.common.MusicAction

/**
 * MusicAction 객체를 NotificationCompat.Action으로 변환하는 확장 함수
 * 미디어 세션의 컨트롤 액션으로 사용될 수 있는 Notification.Action을 생성
 */
@UnstableApi
internal fun MusicAction.asNotificationAction(
    context: Context,
    mediaSession: MediaSession,
    actionFactory: MediaNotification.ActionFactory,
) = actionFactory.createMediaAction(
    mediaSession,
    IconCompat.createWithResource(context, iconResource),
    context.getString(titleResource),
    command,
)
