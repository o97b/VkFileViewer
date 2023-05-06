package com.example.vkfileviewer.di

import android.content.Context
import com.example.core.di.AppScope
import com.example.database.di.DatabaseModule
import dagger.BindsInstance
import dagger.Component

@[AppScope Component (modules = [DatabaseModule::class])]
interface AppComponent {
    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance context: Context,
            databaseModule: DatabaseModule
        ): AppComponent
    }
}