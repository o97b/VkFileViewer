package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class FileEntity (
    @PrimaryKey val path: String,
    val hash: String
)

