package com.reminder.presentation.features.task_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.navigation.Screen

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
                    navController.navigate(Screen.AddTask)
                }

                is TaskListScreenActions.OnTaskDetailsClicked -> {
                    navController.navigate(Screen.TaskDetails(action.taskId))
                }
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

            Button(onClick = {
                onAction.invoke(TaskListScreenActions.OnTaskDetailsClicked(1))
            }) {
                Text(text = "Task details")
            }
        }
    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    ReminderTheme {
        TaskListScreen(
            state = TaskListScreenState(),
            onAction = {}
        )
    }
}