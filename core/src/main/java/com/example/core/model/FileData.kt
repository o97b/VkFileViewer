package com.example.core.model

import java.util.*

data class FileData(
    val name: String,
    val size: Long,
    val dateCreated: Date,
    val path: String
)