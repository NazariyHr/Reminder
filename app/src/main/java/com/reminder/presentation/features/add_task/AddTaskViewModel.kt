package com.reminder.presentation.features.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reminder.domain.use_cases.CreateNewTaskUseCase
import com.reminder.presentation.common.utils.formatToDateAndTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
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
                        action.remindDateAnTime?.formatToDateAndTime()
                    )
                    events.send(AddTaskScreenEvents.OnTaskAddedSuccessfully)
                }
            }
        }
    }

    private fun AddTaskScreenActions.AddTask.RemindDateAnTime.formatToDateAndTime(): String? {
        var remindDateAnTimeCalendar: Calendar? = null
        if (remindDate != null) {
            remindDateAnTimeCalendar = Calendar.getInstance().apply {
                timeInMillis = remindDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }
        if (remindTime != null) {
            if (remindDateAnTimeCalendar == null) {
                remindDateAnTimeCalendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
            }
            val remindTimeCalendar =
                Calendar.getInstance().apply { timeInMillis = remindTime }
            remindDateAnTimeCalendar?.set(
                Calendar.HOUR_OF_DAY,
                remindTimeCalendar.get(Calendar.HOUR_OF_DAY)
            )
            remindDateAnTimeCalendar?.set(
                Calendar.MINUTE,
                remindTimeCalendar.get(Calendar.MINUTE)
            )
        }
        return remindDateAnTimeCalendar?.timeInMillis?.formatToDateAndTime()
    }
}