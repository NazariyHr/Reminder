package com.reminder.domain.scheduling.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.reminder.domain.loggers.Logger
import com.reminder.domain.repository.TasksRepository
import com.reminder.domain.scheduling.TaskReminderScheduler
import com.reminder.presentation.common.utils.convertFromDateAndTime
import com.reminder.presentation.common.utils.formatToDateAndTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var tasksRepository: TasksRepository

    @Inject
    lateinit var taskReminderScheduler: TaskReminderScheduler

    @Inject
    lateinit var logger: Logger

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val currTime = Calendar.getInstance().timeInMillis.formatToDateAndTime()
            logger.log("$currTime, ACTION_BOOT_COMPLETED onReceive")

            CoroutineScope(Dispatchers.IO).launch {
                tasksRepository.getAllTasks().forEach { task ->
                    taskReminderScheduler.cancelTaskReminder(task.id)
                    if (task.remindTime != null && task.remindTime.convertFromDateAndTime() > System.currentTimeMillis()) {
                        taskReminderScheduler.scheduleTaskReminder(task)
                    }
                }
            }
        }
    }
}