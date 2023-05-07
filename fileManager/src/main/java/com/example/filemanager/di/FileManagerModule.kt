package com.example.filemanager.di

import android.content.Context
import com.example.filemanager.domain.FileManagerRepository
import com.example.filemanager.domain.FileManagerRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class FileManagerModule {
    @Provides
    fun provideRepository(context: Context): FileManagerRepository {
        return FileManagerRepositoryImpl(context)
    }
}