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
    actionFactory: MediaNotification.ActionFactory
) = actionFactory.createMediaAction(
    mediaSession,
    IconCompat.createWithResource(context, iconResource),
    context.getString(titleResource),
    command
)
