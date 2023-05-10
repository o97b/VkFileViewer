package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.model.FileData

@Entity(tableName = "files")
class FileEntity(
    @PrimaryKey val hash: String
)

fun FileData.toFileEntity(): FileEntity = FileEntity(hash)




