package com.reminder.data.mappers

import com.reminder.data.entity.TaskEntity
import com.reminder.domain.model.Task

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        name = name,
        description = description,
        remindTime = remindTime
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        name = name,
        description = description,
        remindTime = remindTime
    )
}