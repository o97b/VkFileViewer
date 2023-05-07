package com.example.filemanager.model

import android.content.Context
import android.os.Environment
import com.example.core.model.FileData
import java.io.File
import java.util.*

internal class FileManager(private val context: Context) {

    fun fetchAllFiles(): List<FileData> {
        val fileList: MutableList<FileData> = mutableListOf()
        val root = Environment.getExternalStorageDirectory()
        fetchFiles(root, fileList)
        return fileList
    }

    private fun fetchFiles(root: File, fileList: MutableList<FileData>) {
        val stack = Stack<File>()
        stack.push(root)

        while (stack.isNotEmpty()) {
            val currentFile = stack.pop()

            if (currentFile.isDirectory) {
                currentFile.listFiles()?.forEach {
                    stack.push(it)
                }
            } else {
                val name = currentFile.name
                val dateAdded = Date(currentFile.lastModified())
                val path = currentFile.absolutePath
                val size = currentFile.length()

                fileList.add(FileData(
                    name = name,
                    size = size,
                    dateCreated = dateAdded,
                    path = path
                ))
            }
        }
    }
}
