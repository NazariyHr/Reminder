package com.reminder.presentation.features.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminder.domain.use_cases.CreateNewTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val createNewTaskUseCase: CreateNewTaskUseCase
) : ViewModel() {

    private val events = Channel<AddTaskScreenEvents>()
    val eventsFlow = events.receiveAsFlow()

    fun onAction(action: AddTaskScreenActions) {
        when (action) {
            is AddTaskScreenActions.AddTask -> {
                viewModelScope.launch {
                    createNewTaskUseCase(
                        action.name,
                        action.description,
                        action.remindDateAnTime
                    )
                    events.send(AddTaskScreenEvents.OnTaskAddedSuccessfully)
                }
            }
        }
    }
}