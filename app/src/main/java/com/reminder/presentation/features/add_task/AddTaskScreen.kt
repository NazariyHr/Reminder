package com.reminder.presentation.features.add_task

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.reminder.presentation.common.components.DatePicker
import com.reminder.presentation.common.components.TimePicker
import com.reminder.presentation.common.components.event_handling.ObserveAsEvent
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.common.utils.formatDate
import com.reminder.presentation.common.utils.formatTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar

@Composable
fun AddTaskScreenRoot(
    navController: NavController,
    viewModel: AddTaskViewModel =
        hiltViewModel()
) {
    AddTaskScreen(
        events = viewModel.eventsFlow,
        onAction = viewModel::onAction,
        onTaskAdded = {
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    events: Flow<AddTaskScreenEvents>,
    onAction: (AddTaskScreenActions) -> Unit,
    onTaskAdded: () -> Unit
) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var nameFieldError by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    var remindDate by rememberSaveable {
        mutableLongStateOf(0L)
    }
    val remindDateStr by remember {
        derivedStateOf {
            if (remindDate == 0L) {
                ""
            } else {
                remindDate.formatDate()
            }
        }
    }
    var remindTime by rememberSaveable {
        mutableLongStateOf(0L)
    }
    val remindTimeStr by remember {
        derivedStateOf {
            if (remindTime == 0L) {
                ""
            } else {
                remindTime.formatTime()
            }
        }
    }
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    val c = LocalContext.current

    ObserveAsEvent(flow = events) { event ->
        when (event) {
            AddTaskScreenEvents.OnTaskAddedSuccessfully -> {
                Toast.makeText(c, "Task saved", Toast.LENGTH_SHORT).show()
                onTaskAdded()
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgColor)
                .padding(8.dp)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Add new task",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Name *",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                isError = nameFieldError.isNotEmpty(),
                supportingText = if (nameFieldError.isNotEmpty()) {
                    {
                        Text(
                            modifier = Modifier,
                            text = nameFieldError,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                } else null,
                value = name,
                onValueChange = {
                    name = it
                    nameFieldError = ""
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        modifier = Modifier,
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                value = description,
                onValueChange = {
                    description = it
                }
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .clickable {
                            showDatePicker = true
                        }
                        .weight(1f),
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        disabledIndicatorColor = OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                        disabledLabelColor = OutlinedTextFieldDefaults.colors().unfocusedLabelColor,
                        disabledTextColor = OutlinedTextFieldDefaults.colors().unfocusedTextColor
                    ),
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Remind date",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    value = remindDateStr,
                    onValueChange = { }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .clickable {
                            showTimePicker = true
                        }
                        .weight(1f),
                    readOnly = true,
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        disabledIndicatorColor = OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                        disabledLabelColor = OutlinedTextFieldDefaults.colors().unfocusedLabelColor,
                        disabledTextColor = OutlinedTextFieldDefaults.colors().unfocusedTextColor
                    ),
                    label = {
                        Text(
                            modifier = Modifier,
                            text = "Remind time",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    value = remindTimeStr,
                    onValueChange = { }
                )
            }

            Button(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (name.isEmpty()) {
                        nameFieldError = "Enter name of task"
                    } else {
                        var remindDateAnTimeCalendar: Calendar? = null
                        if (remindDate != 0L) {
                            remindDateAnTimeCalendar = Calendar.getInstance().apply {
                                timeInMillis = remindDate
                                set(Calendar.HOUR_OF_DAY, 0)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                        }
                        if (remindTime != 0L) {
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

                        onAction.invoke(
                            AddTaskScreenActions.AddTask(
                                name = name,
                                description = description.ifEmpty { null },
                                remindDateAnTime = remindDateAnTimeCalendar?.timeInMillis
                            )
                        )
                    }
                }
            ) {
                Text(text = "Save")
            }

            AnimatedVisibility(visible = showDatePicker) {
                DatePicker(
                    onDateSelected = { date ->
                        if (date != null) {
                            remindDate = date
                        }
                    },
                    onDismiss = {
                        showDatePicker = false
                    }
                )
            }
            AnimatedVisibility(visible = showTimePicker) {
                TimePicker(
                    onConfirm = { state ->
                        val c = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, state.hour)
                            set(Calendar.MINUTE, state.minute)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        remindTime = c.timeInMillis
                    },
                    onDismiss = {
                        showTimePicker = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddTaskScreenPreview() {
    ReminderTheme {
        AddTaskScreen(
            events = flowOf(),
            onAction = {},
            onTaskAdded = {}
        )
    }
}