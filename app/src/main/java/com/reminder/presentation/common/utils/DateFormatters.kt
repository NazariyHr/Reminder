package com.reminder.presentation.common.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.formatToDateAndTime(): String {
    val timeToConvert = this
    val date: Date = Calendar.getInstance().apply { timeInMillis = timeToConvert }.time
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(date)
}

fun Long.formatDate(): String {
    val timeToConvert = this
    val date: Date = Calendar.getInstance().apply { timeInMillis = timeToConvert }.time
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date)
}

fun Long.formatTime(): String {
    val timeToConvert = this
    val date: Date = Calendar.getInstance().apply { timeInMillis = timeToConvert }.time
    val dateFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return dateFormat.format(date)
}