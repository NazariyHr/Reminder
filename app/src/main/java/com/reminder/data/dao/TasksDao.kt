package com.reminder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.reminder.data.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("select * from taskentity")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("select * from taskentity")
    fun getAllTasksFlow(): Flow<List<TaskEntity>>

    @Query("delete from taskentity where id = :taskId")
    suspend fun deleteTask(taskId: Int)
}