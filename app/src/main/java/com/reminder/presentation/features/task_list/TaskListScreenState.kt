package com.reminder.presentation.features.task_list

import android.os.Parcelable
import com.reminder.domain.model.Task
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskListScreenState(
    val tasks: List<Task> = emptyList(),
    val schedulingLog: String = "",
    val showLog: Boolean = false //set true if you want to see logs from alerts scheduling and receivers for rescheduling
) : Parcelable