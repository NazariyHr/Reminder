package com.reminder.presentation.features.task_details

import android.os.Parcelable
import com.reminder.domain.model.Task
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDetailsScreenState(
    val task: Task? = null
) : Parcelable