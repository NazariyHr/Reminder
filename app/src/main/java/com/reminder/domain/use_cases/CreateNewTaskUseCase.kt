package com.reminder.domain.use_cases

import com.reminder.domain.repository.TasksRepository
import com.reminder.domain.scheduling.TaskReminderScheduler
import javax.inject.Inject

data class CreateNewTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderScheduler: TaskReminderScheduler
) {
    suspend operator fun invoke(
        name: String,
        description: String?,
        remindTime: String?
    ) {
        val createdTask = tasksRepository.addNewTask(name, description, remindTime)
        taskReminderScheduler.scheduleTaskReminder(createdTask)
    }
}