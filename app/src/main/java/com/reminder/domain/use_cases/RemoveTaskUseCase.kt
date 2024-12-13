package com.reminder.domain.use_cases

import com.reminder.domain.repository.TasksRepository
import com.reminder.domain.scheduling.TaskReminderScheduler
import javax.inject.Inject

data class RemoveTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderScheduler: TaskReminderScheduler
) {
    suspend operator fun invoke(
        taskId: Int
    ) {
        tasksRepository.removeTask(taskId)
        taskReminderScheduler.cancelTaskReminder(taskId)
    }
}