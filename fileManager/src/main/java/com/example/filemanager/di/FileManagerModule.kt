package com.example.filemanager.di

import android.content.Context
import com.example.core.di.AppScope
import com.example.filemanager.domain.FileManagerRepository
import com.example.filemanager.domain.FileManagerRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class FileManagerModule {
    @AppScope
    @Provides
    fun provideFileManagerRepository(context: Context): FileManagerRepository {
        return FileManagerRepositoryImpl(context)
    }
}