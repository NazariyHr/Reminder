package com.reminder.presentation.features.task_list

sealed class TaskListScreenActions {
    data object OnAddNewTaskClicked : TaskListScreenActions()
    data class OnTaskDetailsClicked(val taskId: Int) : TaskListScreenActions()
}