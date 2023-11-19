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

package com.najudoryeong.musicdo.core.common.dispatcher.di

import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.DEFAULT
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.IO
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.MAIN
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.UNCONFINED
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * dispatcher 주입 모듈
 * 1. DEFAULT : CPU 집약 작업
 * 2. MAIN : UI
 * 3. IO : 파일 입출력 및 네트워크
 * 4. UNCONFINED
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(UNCONFINED)
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    @Provides
    @Dispatcher(IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
