package com.example.core.model

import java.text.SimpleDateFormat
import java.util.*

data class FileData(
    val name: String,
    val size: Long,
    val dateCreated: Date,
    val path: String
){
    fun displayedDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(dateCreated)
    }

    fun displayedName(): String {
        if (name.length <= NAME_LENGTH) return name

        val extensionStart = name.lastIndexOf('.')
        if (extensionStart == -1) {
            return name.take(NAME_LENGTH - TRUNCATION_MARKER.length) + TRUNCATION_MARKER
        }

        val extension = name.substring(extensionStart)
        val baseName = name.substring(0, extensionStart)

        if (baseName.length <= BASE_NAME_LENGTH) return name

        val formattedBaseName = baseName.take(BASE_NAME_DISPLAY_LENGTH) +
                TRUNCATION_MARKER + baseName.takeLast(BASE_NAME_TAIL_DISPLAY_LENGTH)

        return formattedBaseName + extension
    }

    fun displayedSize(): String {
        val kiloByte = 1024.0
        val megaByte = kiloByte * 1024
        val gigaByte = megaByte * 1024

        return when {
            size >= gigaByte -> String.format("%.1f ГБ", size / gigaByte)
            size >= megaByte -> String.format("%.1f МБ", size / megaByte)
            else -> String.format("%.1f КБ", size / kiloByte)
        }
    }

    companion object {
        private const val NAME_LENGTH = 10
        private const val BASE_NAME_LENGTH = 5
        private const val BASE_NAME_DISPLAY_LENGTH = 2
        private const val BASE_NAME_TAIL_DISPLAY_LENGTH = 2
        private const val TRUNCATION_MARKER = "..."

        const val DATE_FORMAT = "dd.MM.yyyy"
    }
}