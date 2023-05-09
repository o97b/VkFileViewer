package com.example.database.domain

import com.example.database.FileDao
import com.example.database.entities.FileEntity

interface DataBaseRepository {
    suspend fun getFileHashes(): List<FileEntity>
    suspend fun insertFileHashes(files: List<FileEntity>)
}

class DataBaseRepositoryImpl(private val fileDao: FileDao): DataBaseRepository {
    override suspend fun getFileHashes(): List<FileEntity> {
        return fileDao.getFileHashes()
    }
    override suspend fun insertFileHashes(files: List<FileEntity>){
        fileDao.insertFileHashes(files)
    }
}