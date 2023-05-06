package com.example.vkfileviewer

import android.app.Application
import com.example.database.di.DatabaseModule
import com.example.vkfileviewer.di.AppComponent
import com.example.vkfileviewer.di.DaggerAppComponent

class VkFileManagerApp: Application() {
    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(
                applicationContext,
                DatabaseModule()
            )
    }
}