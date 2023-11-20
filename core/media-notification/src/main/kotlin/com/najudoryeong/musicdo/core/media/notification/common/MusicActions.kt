package com.najudoryeong.musicdo.core.media.notification.common

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.media.notification.util.asNotificationAction
import com.najudoryeong.musicdo.core.media.common.R as mediaCommonR


 /**
 * 미디어 플레이어 알림에 표시될 다양한 액션을 생성하는 유틸리티 object
 * 각 함수는 특정 미디어 컨트롤 액션에 대응하는 Notification.Action 객체를 생성
 */

 @UnstableApi internal object MusicActions {
    internal fun getRepeatShuffleAction(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory
    ) = actionFactory.createCustomActionFromCustomCommandButton(mediaSession, customLayout.first())

    internal fun getSkipPreviousAction(
        context: Context,
        mediaSession: MediaSession,
        actionFactory: MediaNotification.ActionFactory
    ) = MusicAction(
        iconResource = DoIcons.SkipPrevious.resourceId,
        titleResource = mediaCommonR.string.skip_previous,
        command = Player.COMMAND_SEEK_TO_PREVIOUS
    ).asNotificationAction(context, mediaSession, actionFactory)

    internal fun getPlayPauseAction(
        context: Context,
        mediaSession: MediaSession,
        actionFactory: MediaNotification.ActionFactory,
        playWhenReady: Boolean
    ) = MusicAction(
        iconResource = if (playWhenReady) DoIcons.Pause.resourceId else DoIcons.Play.resourceId,
        titleResource = if (playWhenReady) mediaCommonR.string.pause else mediaCommonR.string.play,
        command = Player.COMMAND_PLAY_PAUSE
    ).asNotificationAction(context, mediaSession, actionFactory)

    internal fun getSkipNextAction(
        context: Context,
        mediaSession: MediaSession,
        actionFactory: MediaNotification.ActionFactory
    ) = MusicAction(
        iconResource = DoIcons.SkipNext.resourceId,
        titleResource = mediaCommonR.string.skip_next,
        command = Player.COMMAND_SEEK_TO_NEXT
    ).asNotificationAction(context, mediaSession, actionFactory)

    internal fun getFavoriteAction(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory
    ) = actionFactory.createCustomActionFromCustomCommandButton(mediaSession, customLayout.last())
}
