package com.najudoryeong.musicdo.core.datastore.serializer

import javax.inject.Inject
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.najudoryeong.musicdo.core.datastore.UserPreferences
import java.io.InputStream
import java.io.OutputStream

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
