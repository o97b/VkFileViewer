package com.example.filelist.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private const val DATE_FORMAT = "dd.MM.yyyy"

    fun format(date: Date): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }
}