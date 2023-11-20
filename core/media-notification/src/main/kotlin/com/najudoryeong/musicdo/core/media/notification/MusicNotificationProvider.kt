package com.najudoryeong.musicdo.core.media.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper
import com.google.common.collect.ImmutableList
import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.*
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.media.notification.common.MusicActions
import com.najudoryeong.musicdo.core.media.notification.util.asArtworkBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * 미디어 세션과 관련된 알림을 생성하고 관리하는 역할을 수행하는 클래스 [MediaNotification.Provider] 인터페이스를 구현
 */
@UnstableApi
class MusicNotificationProvider @Inject constructor(
    @Dispatcher(MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : MediaNotification.Provider {
    private val notificationManager = checkNotNull(context.getSystemService<NotificationManager>())
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())


    /**
     * 미디어 세션에서 노티피케이션을 생성하는 함수
     */
    override fun createNotification(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        onNotificationChangedCallback: MediaNotification.Provider.Callback
    ): MediaNotification {
        ensureNotificationChannel()

        val player = mediaSession.player
        val metadata = player.mediaMetadata

        val builder = NotificationCompat.Builder(context, MusicNotificationChannelId)
            .setContentTitle(metadata.title)
            .setContentText(metadata.artist)
            .setSmallIcon(DoIcons.Music.resourceId)
            .setStyle(MediaStyleNotificationHelper.MediaStyle(mediaSession))
            .setContentIntent(mediaSession.sessionActivity)

        getNotificationActions(
            mediaSession = mediaSession,
            customLayout = customLayout,
            actionFactory = actionFactory,
            playWhenReady = player.playWhenReady
        ).forEach(builder::addAction)

        setupArtwork(
            uri = metadata.artworkUri,
            setLargeIcon = builder::setLargeIcon,
            updateNotification = {
                val notification = MediaNotification(MusicNotificationId, builder.build())
                onNotificationChangedCallback.onNotificationChanged(notification)
            }
        )

        return MediaNotification(MusicNotificationId, builder.build())
    }

    override fun handleCustomCommand(session: MediaSession, action: String, extras: Bundle) = false

    fun cancelCoroutineScope() = coroutineScope.cancel()

    /**
     * [MusicNotificationChannelId]로 식별되는 알림 채널을 생성
     */
    private fun ensureNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O ||
            notificationManager.getNotificationChannel(MusicNotificationChannelId) != null
        ) {
            return
        }

        val notificationChannel = NotificationChannel(
            MusicNotificationChannelId,
            context.getString(R.string.music_notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    /**
     * 미디어 알림에 표시할 액션 버튼 리스트 반환
     */
    private fun getNotificationActions(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        playWhenReady: Boolean
    ) = listOf(
        MusicActions.getRepeatShuffleAction(mediaSession, customLayout, actionFactory),
        MusicActions.getSkipPreviousAction(context, mediaSession, actionFactory),
        MusicActions.getPlayPauseAction(context, mediaSession, actionFactory, playWhenReady),
        MusicActions.getSkipNextAction(context, mediaSession, actionFactory),
        MusicActions.getFavoriteAction(mediaSession, customLayout, actionFactory)
    )

    /**
     * 앨범 아트워크를 비동기적으로 로드하고, 이를 알림의 큰 아이콘으로 설정 함수
     */
    private fun setupArtwork(
        uri: Uri?,
        setLargeIcon: (Bitmap?) -> Unit,
        updateNotification: () -> Unit
    ) = coroutineScope.launch {
        val bitmap = loadArtworkBitmap(uri)
        setLargeIcon(bitmap)
        updateNotification()
    }

    /**
     * Uri를 통해 앨범 아트워크의 비트맵을 로드
     */
    private suspend fun loadArtworkBitmap(uri: Uri?) =
        withContext(ioDispatcher) { uri?.asArtworkBitmap(context) }
}

private const val MusicNotificationId = 1001
private const val MusicNotificationChannelId = "MusicNotificationChannel"
