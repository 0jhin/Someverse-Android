package com.someverse.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.someverse.presentation.ui.theme.PrimaryPurple

@Composable
fun PageIndicator(isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(
                width = if (isActive) 32.dp else 8.dp,
                height = 8.dp
            )
            .clip(CircleShape)
            .background(
                if (isActive) PrimaryPurple else Color(0xFFE4E8EF)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun PageIndicatorPreView() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(5) { index ->
            PageIndicator(isActive = index == 0)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}