package com.reminder.domain.scheduling

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.reminder.R
import com.reminder.domain.model.Task

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "reminders"
        const val NOTIFICATION_CHANNEL_NAME = "Task reminders"

        const val EXTRA_TASK = "TASK"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SchedulingConstants.ACTION_ALARM_TRIGGERED) {
            val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_TASK) as? Task
            }
            if (task != null) {
                showReminderNotification(task, context)
            }
        }
    }

    private fun showReminderNotification(task: Task, context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for reminders notifications"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(task.name)
            .setContentText("It's time to do this task.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(task.id, builder.build())
    }
}