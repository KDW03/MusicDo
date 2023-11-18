package com.najudoryeong.musicdo.core.mediastore.util

import android.provider.MediaStore
import com.najudoryeong.musicdo.core.model.SortBy
import com.najudoryeong.musicdo.core.model.SortOrder

/**
 * 정렬 순서 쿼리문 생성
 */
internal fun buildMediaStoreSortOrder(sortOrder: SortOrder, sortBy: SortBy): String {
    val mediaStoreSortOrder = mediaStoreSortOrderMap.getValue(sortOrder)
    val mediaStoreSortBy = mediaStoreSortByMap.getValue(sortBy)
    return "$mediaStoreSortBy $mediaStoreSortOrder"
}

private val mediaStoreSortOrderMap = mapOf(
    SortOrder.ASCENDING to "ASC",
    SortOrder.DESCENDING to "DESC"
)

private val mediaStoreSortByMap = mapOf(
    SortBy.TITLE to MediaStore.Audio.Media.TITLE,
    SortBy.ARTIST to MediaStore.Audio.Media.ARTIST,
    SortBy.ALBUM to MediaStore.Audio.Media.ALBUM,
    SortBy.DURATION to MediaStore.Audio.Media.DURATION,
    SortBy.DATE to MediaStore.Audio.Media.DATE_ADDED
)
