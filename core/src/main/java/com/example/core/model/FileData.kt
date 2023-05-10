package com.example.core.model

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*

data class FileData(
    val name: String,
    val size: Long,
    val dateCreated: Date,
    val path: String,
    val hash: String,
)

fun FileData.toFileUi(): FileUi {
    return FileUi(
        name,
        size,
        dateCreated,
        path
    )
}

