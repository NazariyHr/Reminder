package com.reminder.domain.loggers

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedPreferencesLogger(c: Context) : Logger {

    companion object {
        const val PREF_LOG = "LOG"
    }

    private val prefs = c.getSharedPreferences("log", Context.MODE_PRIVATE)

    private val logState = MutableStateFlow("")
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            logState.emit(getLogHistory())
        }
    }

    override fun log(message: String) {
        val newLog = prefs.getString(PREF_LOG, "") + message + "\n"
        prefs.edit { putString(PREF_LOG, newLog) }
        scope.launch { logState.emit(newLog) }
    }

    private fun getLogHistory(): String {
        return prefs.getString(PREF_LOG, "").orEmpty()
    }

    override fun getLogHistoryFlow(): StateFlow<String> {
        return logState.asStateFlow()
    }

    override fun clearLogHistory() {
        prefs.edit {
            putString(PREF_LOG, "")
            scope.launch {
                logState.emit("")
            }
        }
    }
}