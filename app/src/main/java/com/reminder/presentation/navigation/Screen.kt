package com.reminder.presentation.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TaskList : Screen()

    @Serializable
    @Parcelize
    data class TaskDetails(val taskId: Int) : Screen(), Parcelable

    @Serializable
    data object AddTask : Screen()
}