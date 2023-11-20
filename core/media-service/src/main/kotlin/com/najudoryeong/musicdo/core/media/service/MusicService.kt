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

package com.najudoryeong.musicdo.core.media.service

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.MAIN
import com.najudoryeong.musicdo.core.domain.usecase.GetFavoriteSongIdsUseCase
import com.najudoryeong.musicdo.core.domain.usecase.GetPlaybackModeUseCase
import com.najudoryeong.musicdo.core.media.notification.MusicNotificationProvider
import com.najudoryeong.musicdo.core.media.service.utiil.unsafeLazy
import com.najudoryeong.musicdo.core.model.PlaybackMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 미디어 세션과 관련된 기능을 제공하는 서비스 [MediaSessionService] 상속
 */
@UnstableApi
@AndroidEntryPoint
class MusicService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    @Inject
    lateinit var musicSessionCallback: MusicSessionCallback

    @Inject
    lateinit var musicNotificationProvider: MusicNotificationProvider

    @Inject
    lateinit var getPlaybackModeUseCase: GetPlaybackModeUseCase

    @Inject
    lateinit var getFavoriteSongIdsUseCase: GetFavoriteSongIdsUseCase

    private val _currentMediaId = MutableStateFlow("")
    private val currentMediaId = _currentMediaId.asStateFlow()

    @Inject
    @Dispatcher(MAIN)
    lateinit var mainDispatcher: CoroutineDispatcher
    private val coroutineScope by unsafeLazy { CoroutineScope(mainDispatcher + SupervisorJob()) }

    override fun onCreate() {
        super.onCreate()

        // AudioAttribute 객체 생성, 컨텐츠 타입 : 음악, 사용 용도 : 미디어 재생
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

        // ExoPlayer 초기화 audioAttributes 적용 및 Nosiy시 자동 일시정지
        val player = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()

        // 세션 액티비티(미디어 세션과 상호작용할 때 보여주는 인터페이스를 제공)를 위한 PendingIntent 생성
        val sessionActivityPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntent(Intent(this@MusicService, Class.forName(DO_ACTIVITY_PACKAGE_NAME)))
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // 미디어 세션 초기화
        mediaSession = MediaSession.Builder(this, player)
            .setCallback(musicSessionCallback)
            .setSessionActivity(sessionActivityPendingIntent)
            .build()
            .apply { player.addListener(PlayerListener()) }

        // 미디어 알림 설정
        setMediaNotificationProvider(musicNotificationProvider)

        // 미디어 세션과 동기화
        startPlaybackModeSync()
        startFavoriteSync()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            clearListener()
            mediaSession = null
        }
        musicSessionCallback.cancelCoroutineScope()
        musicNotificationProvider.cancelCoroutineScope()
        super.onDestroy()
    }

    /**
     * 미디어 재생 모드를 동기화하는 메서드.
     * 사용자가 설정한 재생 모드(반복, 셔플 등)에 따라 미디어 플레이어를 조정합니다.
     */
    private fun startPlaybackModeSync() = coroutineScope.launch {
        getPlaybackModeUseCase().collectLatest { playbackMode ->
            mediaSession?.player?.run {
                when (playbackMode) {
                    PlaybackMode.REPEAT -> {
                        shuffleModeEnabled = false
                        repeatMode = Player.REPEAT_MODE_ALL
                    }

                    PlaybackMode.REPEAT_ONE -> {
                        repeatMode = Player.REPEAT_MODE_ONE
                    }

                    PlaybackMode.SHUFFLE -> {
                        repeatMode = Player.REPEAT_MODE_ALL
                        shuffleModeEnabled = true
                    }
                }
            }
            musicSessionCallback.setPlaybackModeAction(playbackMode)
            mediaSession?.setCustomLayout(musicSessionCallback.customLayout)
        }
    }

    private fun startFavoriteSync() = coroutineScope.launch {
        combine(currentMediaId, getFavoriteSongIdsUseCase()) { currentMediaId, favoriteSongIds ->
            currentMediaId in favoriteSongIds
        }.collectLatest { isCurrentMediaIdFavorite ->
            musicSessionCallback.toggleFavoriteAction(isFavorite = isCurrentMediaIdFavorite)
            mediaSession?.setCustomLayout(musicSessionCallback.customLayout)
        }
    }

    private inner class PlayerListener : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            if (mediaItem == null) return
            _currentMediaId.update { mediaItem.mediaId }
        }
    }
}

private const val DO_ACTIVITY_PACKAGE_NAME = "com.najudoryeong.musicdo.MainActivity"
