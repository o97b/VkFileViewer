package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converters.DateConverter
import com.example.database.entities.FileEntity

@Database(entities = [FileEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun fileDao(): FileDao

}