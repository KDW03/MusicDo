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

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.MAIN
import com.najudoryeong.musicdo.core.domain.usecase.GetPlayingQueueIdsUseCase
import com.najudoryeong.musicdo.core.domain.usecase.GetPlayingQueueIndexUseCase
import com.najudoryeong.musicdo.core.domain.usecase.GetSongsUseCase
import com.najudoryeong.musicdo.core.domain.usecase.SetPlayingQueueIdsUseCase
import com.najudoryeong.musicdo.core.domain.usecase.SetPlayingQueueIndexUseCase
import com.najudoryeong.musicdo.core.media.common.MediaConstants.DEFAULT_INDEX
import com.najudoryeong.musicdo.core.media.common.MediaConstants.DEFAULT_POSITION_MS
import com.najudoryeong.musicdo.core.media.service.mapper.asMediaItem
import com.najudoryeong.musicdo.core.media.service.utiil.asPlaybackState
import com.najudoryeong.musicdo.core.media.service.utiil.orDefaultTimestamp
import com.najudoryeong.musicdo.core.model.MusicState
import com.najudoryeong.musicdo.core.model.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

/**
 * 음악 재생 서비스와의 연결 제어를 관리하는 클래스
 */
@UnstableApi
@Singleton
class MusicServiceConnection @Inject constructor(
    @ApplicationContext context: Context,
    @Dispatcher(MAIN) mainDispatcher: CoroutineDispatcher,
    private val getSongsUseCase: GetSongsUseCase,
    private val getPlayingQueueIdsUseCase: GetPlayingQueueIdsUseCase,
    private val setPlayingQueueIdsUseCase: SetPlayingQueueIdsUseCase,
    private val getPlayingQueueIndexUseCase: GetPlayingQueueIndexUseCase,
    private val setPlayingQueueIndexUseCase: SetPlayingQueueIndexUseCase,
) {
    private var mediaController: MediaController? = null
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val _musicState = MutableStateFlow(MusicState())
    val musicState = _musicState.asStateFlow()

    /**
     * 현재 재생 위치 Flow
     * 현재 코루틴 컨텍스트가 활성 상태일 동안, 현재 재생 위치를 지속적으로 방출
     *
     */
    val currentPosition = flow {
        while (currentCoroutineContext().isActive) {
            val currentPosition = mediaController?.currentPosition ?: DEFAULT_POSITION_MS
            emit(currentPosition)
            delay(1.milliseconds)
        }
    }

    /**
     * MusicService와 연결하여 mediaController를 초기화 및 재생 목록 업데이트
     */
    init {
        coroutineScope.launch {
            // mediaController 초기화
            mediaController = MediaController.Builder(
                context,
                SessionToken(context, ComponentName(context, MusicService::class.java)),
            ).buildAsync().await().apply { addListener(PlayerListener()) }
            updatePlayingQueue()
        }
    }

    /** 이전 노래로 이동 후 재생 함수 */
    fun skipPrevious() = mediaController?.run {
        seekToPrevious()
        play()
    }

    /** 노래 재생 함수 */
    fun play() = mediaController?.play()

    /** 노래 일시 중지 함수 */
    fun pause() = mediaController?.pause()

    /** 다음 노래로 이동 후 재생 함수 */
    fun skipNext() = mediaController?.run {
        seekToNext()
        play()
    }

    /** 특정 재생 시간 position으로 이동 후 재생 함수  */
    fun skipTo(position: Long) = mediaController?.run {
        seekTo(position)
        play()
    }

    /** 지정된 인덱스의 노래로 이동후 지정된 position의 시간에서 재생을 시작 */
    fun skipToIndex(index: Int, position: Long = DEFAULT_POSITION_MS) = mediaController?.run {
        seekTo(index, position)
        play()
    }

    /** 시작 인덱스와 시작 Positon을 매개변수로 주어진 노래 목록을 재생 */
    fun playSongs(
        songs: List<Song>,
        startIndex: Int = DEFAULT_INDEX,
        startPositionMs: Long = DEFAULT_POSITION_MS,
    ) {
        mediaController?.run {
            setMediaItems(songs.map(Song::asMediaItem), startIndex, startPositionMs)
            prepare()
            play()
        }
        // 재생 목록 IDS 설정 Using 코루틴
        coroutineScope.launch { setPlayingQueueIdsUseCase(playingQueueIds = songs.map(Song::mediaId)) }
    }

    /**
     *  노래 목록을 무작위로 섞은 후 재생하는 함수
     */
    fun shuffleSongs(
        songs: List<Song>,
        startIndex: Int = DEFAULT_INDEX,
        startPositionMs: Long = DEFAULT_POSITION_MS,
    ) = playSongs(
        songs = songs.shuffled(),
        startIndex = startIndex,
        startPositionMs = startPositionMs,
    )

    /**
     * Player의 이벤트를 감지하는 리스너 클래스
     */
    private inner class PlayerListener : Player.Listener {

        /**
         * Player의 이벤트가 발생할 때 호출
         *
         * @param player 현재 미디어 플레이어 객체
         * @param events 발생한 이벤트 목록
         */
        override fun onEvents(player: Player, events: Player.Events) {
            // 재생 상태, 미디어 메타데이터, 재생 준비 여부가 변경되었을 때 음악 상태를 업데이트
            if (events.containsAny(
                    Player.EVENT_PLAYBACK_STATE_CHANGED,
                    Player.EVENT_MEDIA_METADATA_CHANGED,
                    Player.EVENT_PLAY_WHEN_READY_CHANGED,
                )
            ) {
                updateMusicState(player)
            }

            // 미디어 아이템이 전환되었을 때 재생 목록 인덱스를 업데이트
            if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)) {
                updatePlayingQueueIndex(player)
            }
        }
    }

    /**
     * player 정보로 musicState 업데이트
     */
    private fun updateMusicState(player: Player) = with(player) {
        _musicState.update {
            it.copy(
                currentMediaId = currentMediaItem?.mediaId.orEmpty(),
                playbackState = playbackState.asPlaybackState(),
                playWhenReady = playWhenReady,
                duration = duration.orDefaultTimestamp(),
            )
        }
    }

    /**
     * 현재 재생 목록을 업데이트하는 함수
     * getSongsUseCase()를 통해 전체 노래 목록을 가져오고,
     * getPlayingQueueIdsUseCase()를 사용하여 현재 재생 목록에 있는 노래의 ID 목록을 가져옵니다
     * 만약 재생 목록이 비어 있으면 전체 노래 목록을 재생 목록으로 설정합니다.
     *
     * @param startPositionMs 시작 위치 (밀리초 단위).
     */
    private suspend fun updatePlayingQueue(startPositionMs: Long = DEFAULT_POSITION_MS) {
        val songs = getSongsUseCase().first()
        if (songs.isEmpty()) return

        val playingQueueSongs = getPlayingQueueIdsUseCase().first().mapNotNull { playingQueueId ->
            songs.find { it.mediaId == playingQueueId }
        }.ifEmpty {
            // 비어있다면 전체 노래 목록을 재생 목록으로
            setPlayingQueueIdsUseCase(playingQueueIds = songs.map(Song::mediaId))
            songs
        }

        /**
         * 현재 재생 목록의 시작 인덱스를 결정
         */
        val startIndex = getPlayingQueueIndexUseCase().first().let { startIndex ->
            if (startIndex < playingQueueSongs.size) {
                startIndex
            } else {
                setPlayingQueueIndexUseCase(index = 0)
                0
            }
        }

        mediaController?.run {
            setMediaItems(playingQueueSongs.map(Song::asMediaItem), startIndex, startPositionMs)
            prepare()
        }
    }

    /**
     * 현재 재생 중인 미디어 아이템의 인덱스를 업데이트하는 함수
     * Player 객체의 현재 미디어 아이템 인덱스를 사용하여 현재 재생 상태를 업데이트
     * 이 인덱스는 미디어 컨트롤러와 연동되어 현재 재생 중인 노래를 나타냅니다.
     *
     * @param player 현재 미디어 플레이어 객체.
     */
    private fun updatePlayingQueueIndex(player: Player) {
        val index = player.currentMediaItemIndex
        _musicState.update { it.copy(currentSongIndex = index) }
        coroutineScope.launch { setPlayingQueueIndexUseCase(index) }
    }
}
