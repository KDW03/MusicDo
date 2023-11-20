package com.najudoryeong.musicdo.core.media.service

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.media.common.R as mediaCommonR
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.*
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons
import com.najudoryeong.musicdo.core.domain.usecase.SetPlaybackModeUseCase
import com.najudoryeong.musicdo.core.domain.usecase.ToggleFavoriteSongUseCase
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.FAVORITE
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.FAVORITE_OFF
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.FAVORITE_ON
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE_REPEAT
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE_REPEAT_ONE
import com.najudoryeong.musicdo.core.media.notification.common.MusicCommands.PLAYBACK_MODE_SHUFFLE
import com.najudoryeong.musicdo.core.model.PlaybackMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 미디어 세션과 관련된 사용자 정의 커맨드(명령)를 처리하는 역할을 하는 클래스
 */
class MusicActionHandler @Inject constructor(
    @Dispatcher(MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val setPlaybackModeUseCase: SetPlaybackModeUseCase,
    private val toggleFavoriteSongUseCase: ToggleFavoriteSongUseCase
) {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    val customCommands = getAvailableCustomCommands()

    // custom 미디어 컨트롤 레이아웃을 관리하는 맵
    private val customLayoutMap = mutableMapOf<String, CommandButton>().apply {
        this[PLAYBACK_MODE] = customCommands.getValue(PLAYBACK_MODE_REPEAT)
        this[FAVORITE] = customCommands.getValue(FAVORITE_OFF)
    }


    val customLayout: List<CommandButton> get() = customLayoutMap.values.toList()

    /**
     * 미디어 세션에서 발생하는 사용자 정의 커맨드를 처리하는 함수
     * 재생 모드 변경과 즐겨찾기 상태 토글을 처리
     *
     * @param mediaSession 현재 미디어 세션
     * @param customCommand 발생한 사용자 정의 커맨드
     */
    fun onCustomCommand(mediaSession: MediaSession, customCommand: SessionCommand) {
        when (customCommand.customAction) {
            PLAYBACK_MODE_REPEAT, PLAYBACK_MODE_REPEAT_ONE, PLAYBACK_MODE_SHUFFLE -> {
                handleRepeatShuffleCommand(action = customCommand.customAction)
            }

            FAVORITE_ON, FAVORITE_OFF -> {
                val id = mediaSession.player.currentMediaItem?.mediaId ?: return
                handleFavoriteCommand(action = customCommand.customAction, id = id)
            }
        }
    }


    fun setRepeatShuffleCommand(action: String) =
        customLayoutMap.set(PLAYBACK_MODE, customCommands.getValue(action))

    fun setFavoriteCommand(action: String) =
        customLayoutMap.set(FAVORITE, customCommands.getValue(action))

    fun cancelCoroutineScope() = coroutineScope.cancel()

    /**
     * 재생 모드 변경을 UserData에 적용 하는 함수
     *
     * @param action 변경하려는 재생 모드 액션
     */
    private fun handleRepeatShuffleCommand(action: String) = coroutineScope.launch {
        when (action) {
            PLAYBACK_MODE_REPEAT -> setPlaybackModeUseCase(PlaybackMode.REPEAT_ONE)
            PLAYBACK_MODE_REPEAT_ONE -> setPlaybackModeUseCase(PlaybackMode.SHUFFLE)
            PLAYBACK_MODE_SHUFFLE -> setPlaybackModeUseCase(PlaybackMode.REPEAT)
        }
    }

    /**
     * 즐겨찾기 변경을 UserData에 적용 하는 함수
     *
     * @param action 변경하려는 즐겨찾기 액션
     * @param id 현재 미디어 아이템의 ID
     */
    private fun handleFavoriteCommand(action: String, id: String) = coroutineScope.launch {
        when (action) {
            FAVORITE_ON -> toggleFavoriteSongUseCase(id = id, isFavorite = false)
            FAVORITE_OFF -> toggleFavoriteSongUseCase(id = id, isFavorite = true)
        }
    }


    /**
     * @return Map<String, CommandButton> - command to 사용자 정의 커맨드 버튼 맵
     */

    private fun getAvailableCustomCommands() = mapOf(
        PLAYBACK_MODE_REPEAT to buildCustomCommand(
            action = PLAYBACK_MODE_REPEAT,
            displayName = context.getString(mediaCommonR.string.repeat),
            iconResource = DoIcons.Repeat.resourceId
        ),
        PLAYBACK_MODE_REPEAT_ONE to buildCustomCommand(
            action = PLAYBACK_MODE_REPEAT_ONE,
            displayName = context.getString(mediaCommonR.string.repeat_one),
            iconResource = DoIcons.RepeatOne.resourceId
        ),
        PLAYBACK_MODE_SHUFFLE to buildCustomCommand(
            action = PLAYBACK_MODE_SHUFFLE,
            displayName = context.getString(mediaCommonR.string.shuffle),
            iconResource = DoIcons.Shuffle.resourceId
        ),
        FAVORITE_ON to buildCustomCommand(
            action = FAVORITE_ON,
            displayName = context.getString(mediaCommonR.string.favorite_remove),
            iconResource = DoIcons.FavoriteDrawable.resourceId
        ),
        FAVORITE_OFF to buildCustomCommand(
            action = FAVORITE_OFF,
            displayName = context.getString(mediaCommonR.string.favorite_add),
            iconResource = DoIcons.FavoriteBorderDrawable.resourceId
        )
    )
}

/**
 * 사용자 정의 커맨드 버튼을 생성하는 함수
 * 액션, 표시 이름, 아이콘 리소스를 가진다
 *
 * @param action 커맨드에 연결된 액션
 * @param displayName 커맨드 버튼에 표시될 이름
 * @param iconResource 커맨드 버튼에 사용될 아이콘 리소스
 * @return CommandButton - 구성된 사용자 정의 커맨드 버튼
 */
private fun buildCustomCommand(
    action: String,
    displayName: String,
    @DrawableRes iconResource: Int,
) = CommandButton.Builder()
    .setSessionCommand(SessionCommand(action, Bundle.EMPTY))
    .setDisplayName(displayName)
    .setIconResId(iconResource)
    .build()
