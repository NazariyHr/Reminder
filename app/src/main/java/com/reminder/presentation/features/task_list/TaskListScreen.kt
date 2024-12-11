package com.reminder.presentation.features.task_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.reminder.domain.model.Task
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.features.task_list.components.TaskItem
import com.reminder.presentation.navigation.Screen
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
        onAction = { action ->
            when (action) {
                TaskListScreenActions.OnAddNewTaskClicked -> {
                    viewModel.onAction(action)
                }

                is TaskListScreenActions.OnTaskDetailsClicked -> {
                    navController.navigate(Screen.TaskDetails(action.taskId))
                }

                is TaskListScreenActions.OnRemoveTaskClicked -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun TaskListScreen(
    state: TaskListScreenState,
    onAction: (TaskListScreenActions) -> Unit
) {
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
                text = "TaskList",
                textAlign = TextAlign.Center
            )

            Button(onClick = {
                onAction.invoke(TaskListScreenActions.OnAddNewTaskClicked)
            }) {
                Text(text = "Add task")
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Tasks:",
                textAlign = TextAlign.Start
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(state.tasks) { task ->
                    TaskItem(
                        task = task,
                        modifier = Modifier
                            .clickable {
                                onAction.invoke(TaskListScreenActions.OnTaskDetailsClicked(task.id))
                            }
                            .fillMaxWidth(),
                        onRemoveClicked = {
                            onAction(TaskListScreenActions.OnRemoveTaskClicked(task.id))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    val tasks = mutableListOf<Task>()
    val remindTime = Calendar.getInstance().timeInMillis
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
            onAction = {}
        )
    }
}