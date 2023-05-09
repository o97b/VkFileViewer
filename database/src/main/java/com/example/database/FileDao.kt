package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entities.FileEntity

@Dao
interface FileDao {
    @Query("SELECT * FROM files")
    suspend fun getFileHashes(): List<FileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFileHashes(files: List<FileEntity>)
}