package com.example.filemanager.domain

import android.content.Context
import com.example.core.model.FileData
import com.example.filemanager.model.FileManager

interface FileManagerRepository {
    fun fetchAllFiles(): List<FileData>
}

class FileManagerRepositoryImpl(private val context: Context) : FileManagerRepository {
    override fun fetchAllFiles(): List<FileData> {
        return FileManager(context).fetchAllFiles()
    }
}