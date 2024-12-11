package com.reminder.presentation.features.add_task

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddTaskScreenState(
    val title: String = "Add new task"
) : Parcelable