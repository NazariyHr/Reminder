package com.reminder.domain.loggers

import kotlinx.coroutines.flow.StateFlow

interface Logger {
    fun log(message: String)
    fun getLogHistoryFlow(): StateFlow<String>
    fun clearLogHistory()
}