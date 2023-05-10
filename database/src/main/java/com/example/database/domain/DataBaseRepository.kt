package com.example.database.domain

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.FileDao
import com.example.database.entities.FileEntity

interface DataBaseRepository {

    fun getAllFileHashes(): List<FileEntity>?

    suspend fun insertFileHashes(files: List<FileEntity>?)

    suspend fun clearFileHashesTable()
}

class DataBaseRepositoryImpl(private val fileDao: FileDao): DataBaseRepository {
    override fun getAllFileHashes(): List<FileEntity>? {
        return fileDao.getAllFileHashes()
    }

    override suspend fun insertFileHashes(files: List<FileEntity>?) {
        fileDao.insertFileHashes(files)
    }

    override suspend fun clearFileHashesTable() {
        fileDao.clearFileHashesTable()
    }

}