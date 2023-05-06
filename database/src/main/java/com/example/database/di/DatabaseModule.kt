package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.core.di.AppScope
import com.example.database.AppDataBase
import com.example.database.FileDao
import dagger.Module
import dagger.Provides

private const val DATABASE_NAME = "app_database"

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @AppScope
    @Provides
    fun provideFileDao(appDatabase: AppDataBase): FileDao {
        return appDatabase.fileDao()
    }

}