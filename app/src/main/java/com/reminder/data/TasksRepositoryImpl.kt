package com.reminder.data

import com.reminder.data.entity.TaskEntity
import com.reminder.data.mappers.toTask
import com.reminder.data.mappers.toTaskEntity
import com.reminder.domain.model.Task
import com.reminder.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class TasksRepositoryImpl(
    private val localDataBase: LocalDataBase
) : TasksRepository {
    override fun getAllTasksFlow(): Flow<List<Task>> =
        localDataBase.tasksDao.getAllTasksFlow().distinctUntilChanged()
            .map { taskEntities -> taskEntities.map { taskEntity -> taskEntity.toTask() } }

    override suspend fun addNewTask(name: String, description: String, remindTime: Long) {
        localDataBase.tasksDao.insert(
            TaskEntity(
                name = name,
                description = description,
                remindTime = remindTime
            )
        )
    }

    override suspend fun updateTask(updatedTask: Task) {
        localDataBase.tasksDao.updateTask(updatedTask.toTaskEntity())
    }

    override suspend fun removeTask(taskId: Int) {
        localDataBase.tasksDao.deleteTask(taskId)
    }
}