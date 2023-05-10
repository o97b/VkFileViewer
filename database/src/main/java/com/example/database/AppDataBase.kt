package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.entities.FileEntity

@Database(entities = [FileEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun fileDao(): FileDao

}