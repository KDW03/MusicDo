package com.najudoryeong.musicdo.core.media.service.mapper

import com.najudoryeong.musicdo.core.media.service.utiil.buildPlayableMediaItem
import com.najudoryeong.musicdo.core.model.Song


/**
 * Song 객체를 미디어 플레이어에 의해 재생될 수 있는 형식의 객체인 PlayableMediaItem으로 변환하는 확장 함수
 */
internal fun Song.asMediaItem() = buildPlayableMediaItem(
    mediaId = mediaId,
    artistId = artistId,
    albumId = albumId,
    mediaUri = mediaUri,
    artworkUri = artworkUri,
    title = title,
    artist = artist,
    folder = folder,
    duration = duration,
    date = date,
    isFavorite = isFavorite
)
