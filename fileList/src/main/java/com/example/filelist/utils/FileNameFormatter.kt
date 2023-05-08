package com.example.filelist.utils

object FileNameFormatter {
    private const val NAME_LENGTH = 10
    private const val BASE_NAME_LENGTH = 5
    private const val BASE_NAME_DISPLAY_LENGTH = 2
    private const val BASE_NAME_TAIL_DISPLAY_LENGTH = 2
    private const val TRUNCATION_MARKER = "..."

    fun format(name: String): String {
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
}