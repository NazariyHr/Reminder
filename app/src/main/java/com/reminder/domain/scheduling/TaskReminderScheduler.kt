package com.reminder.domain.scheduling

import com.reminder.domain.model.Task

interface TaskReminderScheduler {
    fun scheduleTaskReminder(task: Task)
    fun cancelTaskReminder(taskId: Int)
}