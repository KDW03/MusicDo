package com.najudoryeong.musicdo.core.mediastore.util

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * 주어진 Uri에 대한 데이터 변경을 옵저빙하는 Flow를 생성하는 확장함수
 */
internal fun ContentResolver.observe(uri: Uri) = callbackFlow {
    val observer = object : ContentObserver(null) {
        /**
         * 변경 상황이 있을 때
         */
        override fun onChange(selfChange: Boolean) {
            trySend(selfChange)
        }
    }
    registerContentObserver(uri, true, observer)
    trySend(true)
    awaitClose { unregisterContentObserver(observer) }
}
