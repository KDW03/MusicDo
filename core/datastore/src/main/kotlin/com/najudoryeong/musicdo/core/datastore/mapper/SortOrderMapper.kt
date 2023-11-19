package com.najudoryeong.musicdo.core.datastore.mapper

import com.najudoryeong.musicdo.core.datastore.SortOrderProto
import com.najudoryeong.musicdo.core.model.SortOrder

/**
 * [SortOrder] to [SortOrderProto]
 */
internal fun SortOrder.asSortOrderProto() = when (this) {
    SortOrder.ASCENDING -> SortOrderProto.SORT_ORDER_ASCENDING
    SortOrder.DESCENDING -> SortOrderProto.SORT_ORDER_DESCENDING
}

/**
 * [SortOrderProto] to [SortOrder]
 */
internal fun SortOrderProto.asSortOrder() = when (this) {
    SortOrderProto.SORT_ORDER_ASCENDING -> SortOrder.ASCENDING

    SortOrderProto.UNRECOGNIZED,
    SortOrderProto.SORT_ORDER_UNSPECIFIED,
    SortOrderProto.SORT_ORDER_DESCENDING -> SortOrder.DESCENDING
}
