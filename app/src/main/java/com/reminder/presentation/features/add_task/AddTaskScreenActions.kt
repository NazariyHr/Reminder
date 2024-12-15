package com.reminder.presentation.features.add_task

sealed class AddTaskScreenActions {
    data class AddTask(
        val name: String,
        val description: String?,
        val remindDateAnTime: RemindDateAnTime?
    ) : AddTaskScreenActions() {
        data class RemindDateAnTime(
            val remindDate: Long?,
            val remindTime: Long?
        )
    }
}