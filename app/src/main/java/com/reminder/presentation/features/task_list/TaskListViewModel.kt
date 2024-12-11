package com.reminder.presentation.features.task_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminder.domain.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {
    companion object {
        const val STATE_KEY = "TaskListViewModel_state"
    }

    private var stateValue: TaskListScreenState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<TaskListScreenState>(STATE_KEY)!!
        }
    val state = savedStateHandle.getStateFlow(STATE_KEY, TaskListScreenState())

    init {
        tasksRepository
            .getAllTasksFlow()
            .onEach {
                stateValue = stateValue.copy(tasks = it)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: TaskListScreenActions) {
        when (action) {
            TaskListScreenActions.OnAddNewTaskClicked -> {
                viewModelScope.launch {
                    val tasksCount = stateValue.tasks.count()
                    val currentTime = Calendar.getInstance().timeInMillis
                    tasksRepository.addNewTask(
                        "Task ${tasksCount + 1}",
                        "description for task ${tasksCount + 1}",
                        currentTime
                    )
                }
            }

            is TaskListScreenActions.OnRemoveTaskClicked -> {
                viewModelScope.launch {
                    tasksRepository.removeTask(action.taskId)
                }
            }

            is TaskListScreenActions.OnTaskDetailsClicked -> {
                // handled in screen root
            }
        }
    }
}