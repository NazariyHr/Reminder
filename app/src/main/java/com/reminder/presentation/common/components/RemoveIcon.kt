package com.reminder.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RemoveIcon(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp
) {
    Box(modifier = modifier.drawBehind {
        drawLine(
            color = Color.Red,
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Red,
            start = Offset(size.width, 0f),
            end = Offset(0f, size.height),
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )
    })
}

@Preview
@Composable
private fun RemoveIconPreview() {
    RemoveIcon(
        modifier = Modifier.size(20.dp)
    )
}