package com.najudoryeong.musicdo.core.mediastore.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * 타임스탬프를 시스템의 날짜와 시간으로 변환하는 확장 함수
 */
internal fun Long.asLocalDateTime() =
    Instant.fromEpochSeconds(this).toLocalDateTime(TimeZone.currentSystemDefault())


