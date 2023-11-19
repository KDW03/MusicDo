package com.najudoryeong.musicdo.core.datastore.mapper

import com.najudoryeong.musicdo.core.datastore.SortByProto
import com.najudoryeong.musicdo.core.model.SortBy

/**
 * [SortBy] to [SortByProto]
 */
internal fun SortBy.asSortByProto() = when (this) {
    SortBy.TITLE -> SortByProto.SORT_BY_TITLE
    SortBy.ARTIST -> SortByProto.SORT_BY_ARTIST
    SortBy.ALBUM -> SortByProto.SORT_BY_ALBUM
    SortBy.DURATION -> SortByProto.SORT_BY_DURATION
    SortBy.DATE -> SortByProto.SORT_BY_DATE
}

/**
 * [SortByProto] to [SortBy]
 */
internal fun SortByProto.asSortBy() = when (this) {
    SortByProto.SORT_BY_TITLE -> SortBy.TITLE
    SortByProto.SORT_BY_ARTIST -> SortBy.ARTIST
    SortByProto.SORT_BY_ALBUM -> SortBy.ALBUM
    SortByProto.SORT_BY_DURATION -> SortBy.DURATION

    SortByProto.UNRECOGNIZED,
    SortByProto.SORT_BY_UNSPECIFIED,
    SortByProto.SORT_BY_DATE -> SortBy.DATE
}
