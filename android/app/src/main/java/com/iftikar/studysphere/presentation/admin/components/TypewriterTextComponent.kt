package com.iftikar.studysphere.presentation.admin.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun TypewriterTextComponent(
    modifier: Modifier,
    text: String,
    text2: String,
    delayMillis: Long = 100
) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        visibleText = ""
        while (true) {
            for (i in text.indices) {
                visibleText = text.substring(0, i + 1)
                delay(delayMillis)
            }
            delay(3000)

            visibleText = text2
            for (i in text2.indices) {
                visibleText = text2.substring(0, i + 1)
                delay(delayMillis)
            }
            delay(3000)
        }
    }

    Text(
        text = visibleText,
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = modifier
    )
}