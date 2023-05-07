package com.example.vkfileviewer

import android.app.Application
import com.example.database.di.DatabaseModule
import com.example.filelist.di.FileListDeps
import com.example.filelist.di.FileListDepsProvider
import com.example.filemanager.di.FileManagerModule
import com.example.vkfileviewer.di.AppComponent
import com.example.vkfileviewer.di.DaggerAppComponent

class VkFileManagerApp: Application(), FileListDepsProvider {
    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(
                applicationContext,
                DatabaseModule(),
                FileManagerModule()
            )
    }

    override fun fileListDepsProvider(): FileListDeps {
        return appComponent
    }
}