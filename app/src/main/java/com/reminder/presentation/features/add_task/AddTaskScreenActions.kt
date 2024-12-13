package com.reminder.presentation.features.add_task

sealed class AddTaskScreenActions {
    data class AddTask(
        val name: String,
        val description: String?,
        val remindDateAnTime: Long?
    ) : AddTaskScreenActions()
}