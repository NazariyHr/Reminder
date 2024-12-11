package com.reminder.presentation.features.task_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.reminder.domain.model.Task
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme

@Composable
fun TaskDetailsScreenRoot(
    navController: NavController,
    viewModel: TaskDetailsViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TaskDetailsScreen(state = state)
}

@Composable
fun TaskDetailsScreen(
    state: TaskDetailsScreenState
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
                text = "Task details (id: ${state.task?.id})",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun TaskDetailsPreview() {
    ReminderTheme {
        TaskDetailsScreen(
            state = TaskDetailsScreenState(
                task = Task(
                    id = 123,
                    name = "Do some job",
                    description = "description of task",
                    remindTime = 11111L
                )
            )
        )
    }
}