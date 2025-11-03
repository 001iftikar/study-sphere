package com.iftikar.studysphere.presentation.admin.registration

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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.studysphere.presentation.admin.components.SignUpInButtonComponent
import com.iftikar.studysphere.presentation.admin.components.SignUpInTextFieldComponent
import com.iftikar.studysphere.presentation.admin.components.TypewriterTextComponent
import com.iftikar.studysphere.presentation.navigation.Routes
import com.iftikar.studysphere.ui.theme.SignInUpBackground

@Composable
fun AdminSignInScreen(
    navHostController: NavHostController,
    viewModel: AdminAccountViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventState by viewModel.event.collectAsStateWithLifecycle(AdminAccountEvent.Idle)
    val isSignupEnabled by remember(state) {
        derivedStateOf {
            state.email.isNotEmpty() &&
                    state.password.length >= 8
        }
    }

    val onAction = viewModel::onAction

    LaunchedEffect(eventState) {
        when (eventState) {
            is AdminAccountEvent.OnLoginSuccessVerified -> {
                navHostController.navigate(Routes.EmailVerificationScreenRoute(name = state.fullName))
            }

            is AdminAccountEvent.OnSuccessUnverified -> {
                navHostController.navigate(Routes.EmailVerificationScreenRoute(name = state.fullName))
            }

            else -> Unit
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(SignInUpBackground))
        ) {
            TypewriterTextComponent(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 144.dp, start = 40.dp),
                text = "Something something",
                text2 = "Anything Anything"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SignUpInTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    onValueChange = { onAction(AdminAccountAction.OnEmailChange(it)) },
                    label = "username or email"
                )
                Spacer(Modifier.height(8.dp))
                SignUpInTextFieldComponent(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    onValueChange = { onAction(AdminAccountAction.OnPasswordChange(it)) },
                    label = "password",
                    visualTransformation = if(state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onAction(AdminAccountAction.OnPasswordVisibilityChange)
                            }
                        ) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = null,
                                tint = if (state.isPasswordVisible) Color.Red else LocalContentColor.current
                            )
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
                SignUpInButtonComponent(
                    enabled = isSignupEnabled,
                    onClick = {
                        onAction(AdminAccountAction.OnLogIn)
                    }
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.Green
                        )
                    } else {
                        Text("Log in")
                    }
                }
                Spacer(Modifier.height(12.dp))
                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
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
