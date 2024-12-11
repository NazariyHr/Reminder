package com.reminder.presentation.features.add_task

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
import com.reminder.presentation.common.theme.MainBgColor
import com.reminder.presentation.common.theme.ReminderTheme

@Composable
fun AddTaskScreenRoot(
    navController: NavController,
    viewModel: AddTaskViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AddTaskScreen(state = state)
}

@Composable
fun AddTaskScreen(
    state: AddTaskScreenState
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
                text = "Add task",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun AddTaskScreenPreview() {
    ReminderTheme {
        AddTaskScreen(
            state = AddTaskScreenState()
        )
    }
}