package com.reminder.domain.repository

import com.reminder.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasksFlow(): Flow<List<Task>>
    suspend fun addNewTask(
        name: String,
        description: String?,
        remindTime: Long?
    )

    suspend fun updateTask(updatedTask: Task)
    suspend fun removeTask(taskId: Int)
}