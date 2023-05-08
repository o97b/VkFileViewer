package com.example.filelist.utils

object SizeFormatter {

    fun format(size: Long): String {
        val kiloByte = 1024.0
        val megaByte = kiloByte * 1024
        val gigaByte = megaByte * 1024

        return when {
            size >= gigaByte -> String.format("%.1f ГБ", size / gigaByte)
            size >= megaByte -> String.format("%.1f МБ", size / megaByte)
            else -> String.format("%.1f КБ", size / kiloByte)
        }
    }
}