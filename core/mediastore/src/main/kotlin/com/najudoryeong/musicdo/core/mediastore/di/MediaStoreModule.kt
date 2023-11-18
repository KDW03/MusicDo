package com.najudoryeong.musicdo.core.mediastore.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * ContentResolver 주입 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
object MediaStoreModule {
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver =
        context.contentResolver
}
