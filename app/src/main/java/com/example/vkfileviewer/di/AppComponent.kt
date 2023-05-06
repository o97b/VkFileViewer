package com.example.vkfileviewer.di

import com.example.core.di.AppScope
import com.example.database.di.DatabaseModule
import dagger.Component

@AppScope
@Component(modules = [DatabaseModule::class])
interface AppComponent {

}