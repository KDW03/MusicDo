package com.najudoryeong.musicdo.core.data.di

import com.najudoryeong.musicdo.core.data.repository.MediaRepositoryImpl
import com.najudoryeong.musicdo.core.data.repository.SettingsRepositoryImpl
import com.najudoryeong.musicdo.core.domain.repository.MediaRepository
import com.najudoryeong.musicdo.core.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindMediaRepository(mediaRepositoryImpl: MediaRepositoryImpl): MediaRepository

    @Binds
    fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
