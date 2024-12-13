package com.reminder.presentation.features.task_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.reminder.domain.repository.TasksRepository
import com.reminder.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {
    companion object {
        const val STATE_KEY = "TaskDetailsViewModel_state"
    }

    private var stateValue: TaskDetailsScreenState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<TaskDetailsScreenState>(STATE_KEY)!!
        }
    val state = savedStateHandle.getStateFlow(STATE_KEY, TaskDetailsScreenState())

    private val taskId = savedStateHandle.toRoute<Screen.TaskDetails>().taskId

    init {
        viewModelScope.launch {
            tasksRepository.getTaskById(taskId)?.let { task ->
                stateValue = stateValue.copy(task = task)
            }
        }
    }
}