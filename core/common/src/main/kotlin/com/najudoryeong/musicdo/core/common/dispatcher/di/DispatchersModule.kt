package com.najudoryeong.musicdo.core.common.dispatcher.di

import com.najudoryeong.musicdo.core.common.dispatcher.Dispatcher
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers
import com.najudoryeong.musicdo.core.common.dispatcher.DoDispatchers.*
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
