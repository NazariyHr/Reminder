package com.reminder.presentation.features.task_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminder.domain.loggers.Logger
import com.reminder.domain.repository.TasksRepository
import com.reminder.domain.use_cases.RemoveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    tasksRepository: TasksRepository,
    private val removeTaskUseCase: RemoveTaskUseCase,
    private val logger: Logger
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

    private val events = Channel<TaskListScreenEvents>()
    val eventsFlow = events.receiveAsFlow()

    init {
        tasksRepository
            .getAllTasksFlow()
            .onEach {
                stateValue = stateValue.copy(tasks = it)
            }
            .launchIn(viewModelScope)
        logger.getLogHistoryFlow()
            .onEach {
                stateValue = stateValue.copy(schedulingLog = it)
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

            TaskListScreenActions.HandledInViewModel.OnClearLogClicked -> {
                logger.clearLogHistory()
            }
        }
    }
}