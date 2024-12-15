package com.reminder.presentation.features.task_list

import android.app.Activity.CLIPBOARD_SERVICE
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.reminder.domain.model.Task
import com.reminder.presentation.common.components.event_handling.ObserveAsEvent
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.common.utils.formatToDateAndTime
import com.reminder.presentation.features.task_list.components.TaskItem
import com.reminder.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar

@Composable
fun TaskListScreenRoot(
    navController: NavController,
    viewModel: TaskListViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TaskListScreen(
        state = state,
        events = viewModel.eventsFlow,
        onAction = { action ->
            if (action is TaskListScreenActions.HandledInRoot) {
                when (action) {
                    TaskListScreenActions.HandledInRoot.OnAddNewTaskClicked -> {
                        navController.navigate(Screen.AddTask)
                    }

                    is TaskListScreenActions.HandledInRoot.OnTaskDetailsClicked -> {
                        navController.navigate(Screen.TaskDetails(action.taskId))
                    }
                }
            } else if (action is TaskListScreenActions.HandledInViewModel) {
                viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun TaskListScreen(
    state: TaskListScreenState,
    events: Flow<TaskListScreenEvents>,
    onAction: (TaskListScreenActions) -> Unit
) {
    val c = LocalContext.current
    ObserveAsEvent(flow = events) { event ->
        when (event) {
            is TaskListScreenEvents.OnLogLoaded -> {
                val clipboard: ClipboardManager = c.getSystemService(
                    CLIPBOARD_SERVICE
                ) as ClipboardManager
                val clip = ClipData.newPlainText("Log", event.log)
                clipboard.setPrimaryClip(clip)
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
                modifier = Modifier.fillMaxWidth(),
                text = "My tasks",
                textAlign = TextAlign.Center
            )

            LazyColumn(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(state.tasks) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier
                            .clickable {
                                onAction.invoke(
                                    TaskListScreenActions.HandledInRoot.OnTaskDetailsClicked(
                                        task.id
                                    )
                                )
                            }
                            .fillMaxWidth(),
                        onRemoveClicked = {
                            onAction(
                                TaskListScreenActions.HandledInViewModel.OnRemoveTaskClicked(
                                    task.id
                                )
                            )
                        }
                    )
                }

                item {
                    Button(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .fillMaxWidth(),
                        onClick = {
                            onAction.invoke(TaskListScreenActions.HandledInRoot.OnAddNewTaskClicked)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add new task",
                        )
                    }
                }

                item {
                    if (state.showLog && state.schedulingLog.isNotEmpty()) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                onAction.invoke(TaskListScreenActions.HandledInViewModel.OnClearLogClicked)
                            }
                        ) {
                            Text(text = "Clear log")
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            text = state.schedulingLog
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    val tasks = mutableListOf<Task>()
    val remindTime = Calendar.getInstance().timeInMillis.formatToDateAndTime()
    var nextId = 1

    repeat(5) {
        tasks.add(
            Task(
                id = nextId++,
                name = "Do something",
                description = "description of this task",
                remindTime = remindTime
            )
        )
    }

    ReminderTheme {
        TaskListScreen(
            state = TaskListScreenState(
                tasks = tasks
            ),
            events = flowOf(),
            onAction = {}
        )
    }
}