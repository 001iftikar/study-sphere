package com.iftikar.studysphere.presentation.admin.components

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

@Composable
fun SignUpInButtonComponent(
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable (() -> Unit)
) {
    Button(
        onClick = onClick,
        enabled = enabled
    ) {
        content()
    }
}