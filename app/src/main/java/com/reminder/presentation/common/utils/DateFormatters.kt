package com.reminder.presentation.common.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.formatToDateAndTime(): String {
    val timeToFormat = this
    val date: Date = Calendar.getInstance().apply { timeInMillis = timeToFormat }.time
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

fun String.convertFromDateAndTime(): Long {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return dateFormat.parse(this)!!.time
}

fun Long.formatDate(): String {
    val timeToFormat = this
    val date: Date = Calendar.getInstance().apply { timeInMillis = timeToFormat }.time
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date)
}

fun Long.formatTime(): String {
    val timeToFormat = this
    val date: Date = Calendar.getInstance().apply { timeInMillis = timeToFormat }.time
    val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}