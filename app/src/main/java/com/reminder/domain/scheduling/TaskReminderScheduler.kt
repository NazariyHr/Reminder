package com.reminder.domain.scheduling

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.reminder.domain.model.Task
import javax.inject.Inject

class TaskReminderScheduler @Inject constructor(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleTaskReminder(task: Task) {
        if (task.remindTime == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = SchedulingConstants.ACTION_ALARM_TRIGGERED
            putExtra(AlarmReceiver.EXTRA_TASK, task)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                task.remindTime,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                task.remindTime,
                pendingIntent
            )
        }
    }

    fun cancelTaskReminder(taskId: Int) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = SchedulingConstants.ACTION_ALARM_TRIGGERED
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