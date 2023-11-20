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

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.MAIN
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.FAVORITE_OFF
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.FAVORITE_ON
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE_REPEAT
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE_REPEAT_ONE
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE_SHUFFLE
import com.najudoryeong.musicdo.core.model.PlaybackMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * 미디어 세션과 관련된 콜백들을 처리하는 클래스 [MediaLibraryService.MediaLibrarySession.Callback] 상속
 */
class MusicSessionCallback @Inject constructor(
    @Dispatcher(MAIN) mainDispatcher: CoroutineDispatcher,
    private val musicActionHandler: MusicActionHandler,
) : MediaLibraryService.MediaLibrarySession.Callback {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    val customLayout: List<CommandButton> get() = musicActionHandler.customLayout

    /**
     * 재생 모드를 설정하는 함수
     * 주어진 재생 모드에 따라 적절한 커맨드를 musicActionHandler에 설정
     *
     * @param playbackMode 현재 설정하려는 재생 모드
     */
    fun setPlaybackModeAction(playbackMode: PlaybackMode) {
        val actionsMap = mapOf(
            PlaybackMode.REPEAT to PLAYBACK_MODE_REPEAT,
            PlaybackMode.REPEAT_ONE to PLAYBACK_MODE_REPEAT_ONE,
            PlaybackMode.SHUFFLE to PLAYBACK_MODE_SHUFFLE,
        )
        musicActionHandler.setRepeatShuffleCommand(actionsMap.getValue(playbackMode))
    }

    /**
     * 즐겨찾기 상태를 토글하는 함수
     * 현재 미디어 아이템의 즐겨찾기 상태를 설정하거나 해제
     *
     * @param isFavorite 현재 미디어 아이템이 즐겨찾기 상태인지 여부
     */
    fun toggleFavoriteAction(isFavorite: Boolean) = musicActionHandler.setFavoriteCommand(if (isFavorite) FAVORITE_ON else FAVORITE_OFF)

    /**
     * 미디어 세션에 미디어 아이템을 추가하는 콜백 함수
     * 미디어 아이템이 미디어 세션에 추가될 때 호출되며, 각 아이템을 처리
     *
     * @param mediaSession 현재 미디어 세션
     * @param controller 미디어 세션을 제어하는 컨트롤러
     * @param mediaItems 추가할 미디어 아이템 목록
     * @return ListenableFuture<List<MediaItem>> 처리된 미디어 아이템 목록을 반환
     */
    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>,
    ): ListenableFuture<List<MediaItem>> = Futures.immediateFuture(
        mediaItems.map { mediaItem ->
            mediaItem.buildUpon()
                .setUri(mediaItem.requestMetadata.mediaUri)
                .build()
        },
    )

    /**
     * 미디어 컨트롤러가 미디어 세션에 연결될 때 호출되는 함수
     *
     * @param session 현재 미디어 세션
     * @param controller 미디어 세션을 제어하는 컨트롤러
     * @return MediaSession.ConnectionResult 연결 결과를 반환
     */
    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
    ): MediaSession.ConnectionResult {
        val connectionResult = super.onConnect(session, controller)
        val availableSessionCommands = connectionResult.availableSessionCommands.buildUpon()

        //     // 사용자 정의 커맨드를 세션 커맨드에 추가
        musicActionHandler.customCommands.values.forEach { commandButton ->
            commandButton.sessionCommand?.let(availableSessionCommands::add)
        }

        // 연결을 수락하고 사용 가능한 커맨드를 반환
        return MediaSession.ConnectionResult.accept(
            availableSessionCommands.build(),
            connectionResult.availablePlayerCommands,
        )
    }

    /**
     * 컨트롤러가 미디어 세션에 연결된 후 호출되는 함수
     * CustomLayout을 세션에 설정
     *
     * @param session 현재 미디어 세션
     * @param controller 미디어 세션을 제어하는 컨트롤러
     */
    override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
        session.setCustomLayout(controller, musicActionHandler.customLayout)
    }

    /**
     * 사용자 정의 커맨드를 처리하는 함수
     * 커맨드에 따라 적절한 액션을 수행
     *
     * @param session 현재 미디어 세션
     * @param controller 미디어 세션을 제어하는 컨트롤러
     * @param customCommand 사용자 정의 커맨드
     *
     * @return ListenableFuture<SessionResult> 비동기 처리 결과를 반환
     */
    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle,
    ): ListenableFuture<SessionResult> {
        // 사용자 정의 커맨드를 처리
        musicActionHandler.onCustomCommand(mediaSession = session, customCommand = customCommand)
        // 사용자 정의 레이아웃을 업데이트
        session.setCustomLayout(musicActionHandler.customLayout)
        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
    }

    /**
     * 리소스 해제
     */
    fun cancelCoroutineScope() {
        coroutineScope.cancel()
        musicActionHandler.cancelCoroutineScope()
    }
}
