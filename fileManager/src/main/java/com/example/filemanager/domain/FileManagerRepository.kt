package com.example.filemanager.domain

import com.example.core.model.FileUi
import com.example.filemanager.model.FileManager
import javax.inject.Inject

interface FileManagerRepository {
    suspend fun fetchAllFiles(): List<FileUi>

    suspend fun getModifiedFileUi(currentFileUi: List<FileUi>): List<FileUi>

    suspend fun updateHashes(currentFileUi: List<FileUi>)
}

class FileManagerRepositoryImpl @Inject constructor(
    private val fileManager: FileManager
) : FileManagerRepository {

    override suspend fun fetchAllFiles(): List<FileUi> {
        return fileManager.fetchFileUi()
    }

    override suspend fun getModifiedFileUi(currentFileUi: List<FileUi>): List<FileUi> {
        return fileManager.getModifiedFileUi(currentFileUi)
    }

    override suspend fun updateHashes(currentFileUi: List<FileUi>) {
        fileManager.updateHashes(currentFileUi)
    }


}