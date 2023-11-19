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

package com.najudoryeong.musicdo.core.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.najudoryeong.musicdo.core.datastore.UserPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * UserPreferences Protocol Buffers 객체를 직렬화 및 역직렬화하는 클래스
 */
class UserPreferencesSerializer @Inject constructor() : Serializer<UserPreferences> {

    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    /**
     * InputStream으로부터 UserPreferences 객체를 읽어들이는 함수
     */
    override suspend fun readFrom(input: InputStream): UserPreferences =
        try {
            UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    /**
     * UserPreferences 객체를 OutputStream에 기록하는 함수
     */
    override suspend fun writeTo(t: UserPreferences, output: OutputStream) =
        t.writeTo(output) // UserPreferences 객체를 OutputStream에 기록합니다.
}
