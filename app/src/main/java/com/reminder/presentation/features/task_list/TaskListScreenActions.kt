package com.reminder.presentation.features.task_list

sealed class TaskListScreenActions {
    sealed class HandledInRoot : TaskListScreenActions() {
        data object OnAddNewTaskClicked : HandledInRoot()
        data class OnTaskDetailsClicked(val taskId: Int) : HandledInRoot()
    }

    sealed class HandledInViewModel : TaskListScreenActions() {
        data class OnRemoveTaskClicked(val taskId: Int) : HandledInViewModel()
    }
}