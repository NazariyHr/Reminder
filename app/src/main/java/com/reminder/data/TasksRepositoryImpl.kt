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

    override suspend fun getAllTasks(): List<Task> {
        return localDataBase.tasksDao.getAllTasks().map { taskEntity -> taskEntity.toTask() }
    }

    override fun getAllTasksFlow(): Flow<List<Task>> =
        localDataBase.tasksDao.getAllTasksFlow().distinctUntilChanged()
            .map { taskEntities -> taskEntities.map { taskEntity -> taskEntity.toTask() } }

    override suspend fun getTaskById(taskId: Int): Task? {
        return localDataBase.tasksDao.getTaskById(taskId)?.toTask()
    }

    override suspend fun addNewTask(name: String, description: String?, remindTime: String?): Task {
        val createdTaskId = localDataBase.tasksDao.insert(
            TaskEntity(
                name = name,
                description = description,
                remindTime = remindTime
            )
        )
        return localDataBase.tasksDao.getTaskById(createdTaskId.toInt())!!.toTask()
    }

    override suspend fun updateTask(updatedTask: Task) {
        localDataBase.tasksDao.updateTask(updatedTask.toTaskEntity())
    }

    override suspend fun removeTask(taskId: Int) {
        localDataBase.tasksDao.deleteTask(taskId)
    }
}