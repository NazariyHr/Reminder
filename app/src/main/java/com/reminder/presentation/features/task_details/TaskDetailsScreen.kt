package com.reminder.presentation.features.task_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.reminder.domain.model.Task
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.common.utils.formatToDateAndTime

@Composable
fun TaskDetailsScreenRoot(
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
                .padding(8.dp)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            if (state.task != null) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = state.task.name,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    if (state.task.description != null) {
                        Text(
                            modifier = Modifier
                                .padding(top = 2.dp),
                            text = state.task.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    if (state.task.remindTime != null) {
                        Row(
                            modifier = Modifier
                                .padding(top = 2.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .padding(end = 2.dp),
                                text = "remind time:",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = state.task.remindTime.formatToDateAndTime(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
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