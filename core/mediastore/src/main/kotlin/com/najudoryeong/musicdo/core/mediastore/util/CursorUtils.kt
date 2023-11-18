package com.najudoryeong.musicdo.core.mediastore.util

import android.database.Cursor



/** 컬럼 이름에 해당하는 인덱스를 통해 여러 타입의 값을 반환 하는 Cusro 확장 함수 */
internal fun Cursor.getLong(columnName: String) = getLong(getColumnIndexOrThrow(columnName))
internal fun Cursor.getInt(columnName: String) = getInt(getColumnIndexOrThrow(columnName))
internal fun Cursor.getString(columnName: String) = getString(getColumnIndexOrThrow(columnName))

