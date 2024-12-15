package com.reminder.domain.scheduling

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.reminder.domain.loggers.Logger
import com.reminder.domain.model.Task
import com.reminder.domain.scheduling.receivers.AlarmReceiver
import com.reminder.presentation.common.utils.convertFromDateAndTime

class TaskReminderSchedulerImpl(
    private val context: Context,
    private val logger: Logger
) : TaskReminderScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("MissingPermission")
    override fun scheduleTaskReminder(task: Task) {
        if (task.remindTime == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ALARM_TRIGGERED
            putExtra(AlarmReceiver.EXTRA_TASK, task)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        logger.log("Schedule task reminder with id:${task.id}, alarm time: ${task.remindTime}\n")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                task.remindTime.convertFromDateAndTime(),
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                task.remindTime.convertFromDateAndTime(),
                pendingIntent
            )
        }
    }

    override fun cancelTaskReminder(taskId: Int) {
        logger.log("Cancel task reminder with id:${taskId}")

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ALARM_TRIGGERED
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}