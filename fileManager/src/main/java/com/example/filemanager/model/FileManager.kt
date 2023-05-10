package com.example.filemanager.model

import android.os.Environment
import com.example.core.model.FileData
import com.example.core.model.FileUi
import com.example.core.model.toFileData
import com.example.core.model.toFileUi
import com.example.database.domain.DataBaseRepository
import com.example.database.entities.FileEntity
import com.example.database.entities.toFileEntity
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import javax.inject.Inject

class FileManager @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    private var fileDataList: List<FileData>? = null

    private var fileDataListCopy: List<FileData>? = null

    private var fileEntitiesFromDb: List<FileEntity>? = null

    private var fileDataListJob: Job? = null

    fun fetchFileUi(): List<FileUi> {
        val fileList: MutableList<FileUi> = mutableListOf()
        val root = Environment.getExternalStorageDirectory()
        fetchFiles(root, fileList)
        return fileList
    }

    suspend fun updateHashes(currentFileUi: List<FileUi>) {
        setFileEntitiesFromDb()
        setFileDataList(currentFileUi)


        fileDataListJob?.let { it ->
            it.join()
            dataBaseRepository.clearFileHashesTable()
            val currentFileEntities = fileDataListCopy?.map { fileData -> fileData.toFileEntity() }
            currentFileEntities?.let {fileEntitiesList ->
                dataBaseRepository.insertFileHashes(fileEntitiesList)
            }
        }
    }

    suspend fun getModifiedFileUi(
        currentFileUi: List<FileUi>
    ): List<FileUi> {
        setFileEntitiesFromDb()
        setFileDataList(currentFileUi)

        val modifiedFiles = mutableListOf<FileUi>()

        fileDataListJob?.let {
            it.join()

            val fileEntitiesHashSet = fileEntitiesFromDb
                ?.map { it.hash }
                ?.toHashSet()
                ?: hashSetOf()

            var i = 0
            for (fileData in fileDataList ?: emptyList()) {
                i++
                if (!fileEntitiesHashSet.contains(fileData.hash)) {
                    modifiedFiles.add(fileData.toFileUi())
                }
            }
        }

        return modifiedFiles
    }

    private fun setFileEntitiesFromDb() {
        if (fileEntitiesFromDb == null) {
            fileEntitiesFromDb = dataBaseRepository
                .getAllFileHashes()
                ?: emptyList()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun setFileDataList(currentFiles: List<FileUi>) {

        val deferred = CompletableDeferred<List<FileData>>()

        fileDataListJob = GlobalScope.launch(Dispatchers.Default) {
            val fileDataList = currentFiles.map { it.toFileData() }
            deferred.complete(fileDataList)
        }

        fileDataList = deferred.await()
        fileDataListCopy = fileDataList?.map { it.copy() }

    }

    private fun fetchFiles(root: File, fileList: MutableList<FileUi>) {
        val stack = Stack<File>()
        stack.push(root)

        while (stack.isNotEmpty()) {
            val currentFile = stack.pop()

            if (currentFile.isDirectory) {
                currentFile.listFiles()?.forEach { stack.push(it) }
            } else {
                currentFile.apply {
                    fileList.add(
                        FileUi(
                            name = name,
                            size = length(),
                            dateCreated = Date(lastModified()),
                            path = absolutePath
                        )
                    )
                }
            }
        }
    }
}
