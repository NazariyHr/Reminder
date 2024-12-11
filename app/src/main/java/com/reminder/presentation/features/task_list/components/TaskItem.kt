package com.reminder.presentation.features.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reminder.domain.model.Task
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.common.utils.formatToDateAndTime
import java.util.Calendar

@Composable
fun TaskItem(
    task: Task,
    onRemoveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column {
            Row {
                Text(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(end = 2.dp),
                    text = "id:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = task.id.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 2.dp),
                text = task.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(top = 2.dp),
                text = task.description,
                style = MaterialTheme.typography.bodyMedium
            )
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
                    text = task.remindTime.formatToDateAndTime(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        RemoveIcon(
            modifier = Modifier
                .clickable {
                    onRemoveClicked()
                }
                .padding(top = 2.dp, end = 2.dp)
                .size(12.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    val remindTime = Calendar.getInstance().timeInMillis
    ReminderTheme {
        TaskItem(
            task = Task(
                id = 1,
                name = "Do something",
                description = "description of this task",
                remindTime = remindTime
            ),
            onRemoveClicked = {}
        )
    }
}