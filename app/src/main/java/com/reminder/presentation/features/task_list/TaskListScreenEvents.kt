package com.reminder.presentation.features.task_list

sealed class TaskListScreenEvents {
    data class OnLogLoaded(val log: String) : TaskListScreenEvents()
}