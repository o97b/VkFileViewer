package com.example.vkfileviewer.di

import android.content.Context
import com.example.core.di.AppScope
import com.example.database.di.DatabaseModule
import com.example.filelist.di.FileListDeps
import com.example.filemanager.di.FileManagerModule
import com.example.filemanager.domain.FileManagerRepository
import dagger.BindsInstance
import dagger.Component

@[AppScope Component (
    modules = [
        DatabaseModule::class,
        FileManagerModule::class
    ]
)]
interface AppComponent: FileListDeps {
    override fun fileManagerRepository(): FileManagerRepository

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance context: Context,
            databaseModule: DatabaseModule,
            fileManagerModule: FileManagerModule
        ): AppComponent
    }
}