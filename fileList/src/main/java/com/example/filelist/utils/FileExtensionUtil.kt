package com.example.filelist.utils

object FileExtensionUtil {
    fun getExtension(fileName: String): String {
        val extensionStart = fileName.lastIndexOf('.')
        return if (extensionStart == -1 || extensionStart == fileName.length - 1) {
            ""
        } else {
            fileName.substring(extensionStart)
        }
    }
}