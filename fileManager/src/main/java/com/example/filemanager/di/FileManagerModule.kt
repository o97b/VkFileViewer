package com.example.filemanager.di

import android.content.Context
import com.example.core.di.AppScope
import com.example.database.domain.DataBaseRepository
import com.example.filemanager.domain.FileManagerRepository
import com.example.filemanager.domain.FileManagerRepositoryImpl
import com.example.filemanager.model.FileManager
import dagger.Module
import dagger.Provides


@Module
class FileManagerModule {
    @AppScope
    @Provides
    fun provideFileManagerRepository(fileManager: FileManager): FileManagerRepository {
        return FileManagerRepositoryImpl(fileManager)
    }
}