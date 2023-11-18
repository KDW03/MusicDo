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

package com.najudoryeong.musicdo.core.mediastore.util

import android.database.Cursor

/** 컬럼 이름에 해당하는 인덱스를 통해 여러 타입의 값을 반환 하는 Cusro 확장 함수 */
internal fun Cursor.getLong(columnName: String) = getLong(getColumnIndexOrThrow(columnName))
internal fun Cursor.getInt(columnName: String) = getInt(getColumnIndexOrThrow(columnName))
internal fun Cursor.getString(columnName: String) = getString(getColumnIndexOrThrow(columnName))
