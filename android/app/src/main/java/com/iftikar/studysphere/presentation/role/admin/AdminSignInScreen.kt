package com.iftikar.studysphere.presentation.role.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iftikar.studysphere.presentation.components.SignUpInButtonComponent
import com.iftikar.studysphere.presentation.components.SignUpInTextFieldComponent
import com.iftikar.studysphere.presentation.navigation.Routes
import com.iftikar.studysphere.ui.theme.SignInUpBackground
import kotlinx.coroutines.delay

@Composable
fun AdminSignInScreen(
    navHostController: NavHostController
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(SignInUpBackground))
        ) {
            TypewriterText(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 144.dp, start = 40.dp),
                text = "Something something",
                text2 = "Anything Anything"
            )

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SignUpInTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = {},
                    label = "username or email"
                )
                Spacer(Modifier.height(8.dp))
                SignUpInTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = {},
                    label = "password",
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                // todo password visibility
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Visibility,
                                contentDescription = null
                            )
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
                SignUpInButtonComponent(
                    title = "log in",
                    onClick = {
                        // todo log in
                    }
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Not registered?")
                    TextButton(
                        onClick = {
                            navHostController.navigate(Routes.AdminRegisterScreenRoute) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text(
                            text = "Register now",
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TypewriterText(
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