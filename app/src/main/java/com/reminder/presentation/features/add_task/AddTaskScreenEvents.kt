package com.reminder.presentation.features.add_task

sealed class AddTaskScreenEvents {
    data object OnTaskAddedSuccessfully : AddTaskScreenEvents()
}