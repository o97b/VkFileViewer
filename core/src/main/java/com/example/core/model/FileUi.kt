package com.example.core.model

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*

data class FileUi (
    val name: String,
    val size: Long,
    val dateCreated: Date,
    val path: String
)

const val BUFFER_SIZE_FOR_HASH = 64 * 1024

fun FileUi.toFileData(): FileData {
    val file = File(path)
    val hash = calculateMD5(file)

    return FileData(
        name,
        size,
        dateCreated,
        path,
        hash
    )
}

private fun calculateMD5(file: File): String {
    val bufferSize = BUFFER_SIZE_FOR_HASH
    val buffer = ByteArray(bufferSize)

    val digest = MessageDigest.getInstance("MD5")

    FileInputStream(file).use { inputStream ->
        var bytesRead: Int

        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            digest.update(buffer, 0, bytesRead)
        }
    }

    val byteArray = digest.digest()

    return byteArray.joinToString("") { "%02x".format(it) }
}

