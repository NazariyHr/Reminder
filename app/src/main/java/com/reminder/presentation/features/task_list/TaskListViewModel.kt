package com.reminder.presentation.features.task_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminder.domain.repository.TasksRepository
import com.reminder.domain.use_cases.RemoveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository,
    private val removeTaskUseCase: RemoveTaskUseCase
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

    fun onAction(action: TaskListScreenActions.HandledInViewModel) {
        when (action) {
            is TaskListScreenActions.HandledInViewModel.OnRemoveTaskClicked -> {
                viewModelScope.launch {
                    removeTaskUseCase(action.taskId)
                }
            }
        }
    }
}