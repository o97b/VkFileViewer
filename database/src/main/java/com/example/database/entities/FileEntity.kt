package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "files")
data class FileEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val size: Long,
    val dateCreated: Date,
    val hash: String
)

