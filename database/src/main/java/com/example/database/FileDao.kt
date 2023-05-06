package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.entities.FileEntity

@Dao
interface FileDao {

    @Query("SELECT * FROM files")
    suspend fun getAllFiles(): List<FileEntity>

    @Insert
    suspend fun insertAll(vararg files: FileEntity)
}