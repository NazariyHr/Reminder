package com.reminder.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reminder.data.dao.TasksDao
import com.reminder.data.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {
    abstract val tasksDao: TasksDao
}