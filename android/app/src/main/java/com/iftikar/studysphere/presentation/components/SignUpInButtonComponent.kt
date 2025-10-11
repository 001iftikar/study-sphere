package com.iftikar.studysphere.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SignUpInButtonComponent(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(text = title)
    }
}