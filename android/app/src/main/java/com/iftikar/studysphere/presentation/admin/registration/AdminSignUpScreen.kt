package com.iftikar.studysphere.presentation.admin.registration

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.studysphere.presentation.admin.components.SignUpInButtonComponent
import com.iftikar.studysphere.presentation.admin.components.SignUpInTextFieldComponent
import com.iftikar.studysphere.presentation.admin.components.TypewriterTextComponent
import com.iftikar.studysphere.ui.theme.SignInUpBackground
import kotlinx.coroutines.launch

@Composable
fun AdminSignUpScreen(
    navHostController: NavHostController,
    viewModel: AdminAccountViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventState by viewModel.event.collectAsStateWithLifecycle(AdminAccountEvent.Idle)
    val isSignupEnabled by remember(state) {
        derivedStateOf {
            state.fullName.isNotEmpty() &&
                    state.username.isNotEmpty() &&
                    state.email.isNotEmpty() &&
                    state.password.length >= 8
        }
    }
    val onAction = viewModel::onAction

    LaunchedEffect(eventState) {
        when(eventState) {
            is AdminAccountEvent.OnSuccessUnverified -> {
                navHostController.navigate((eventState as AdminAccountEvent.OnSuccessUnverified).route)
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {


                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.fullName,
                        onValueChange = { onAction(AdminAccountAction.OnFullNameChange(it)) },
                        label = "Full name",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }

                item {
                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.username,
                        onValueChange = { onAction(AdminAccountAction.OnUsernameChange(it)) },
                        label = "Username",
                    )
                    Spacer(Modifier.height(8.dp))
                }

                item {
                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        onValueChange = { onAction(AdminAccountAction.OnEmailChange(it)) },
                        label = "Email",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }

                item {
                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = { onAction(AdminAccountAction.OnPasswordChange(it)) },
                        label = "Password",
                        visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation()
                        else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        supportingText = "*password must be of 8 characters or long",
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
                }

                state.error?.let { error ->
                    item {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                item {
                    SignUpInButtonComponent(
                        enabled = isSignupEnabled,
                        onClick = {
                            onAction(AdminAccountAction.OnSignUp)
                        }
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.Green
                            )
                        } else {
                            Text("Sign up")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Already have an account?")
                        TextButton(
                            onClick = {
                                navHostController.popBackStack()
                            }
                        ) {
                            Text(
                                text = "Log in",
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }
            }
        }
    }
}

// todo -> will be used ot assign institution to admin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InstitutionSecretKeyField() {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            TooltipAnchorPosition.Above
        ),
        state = tooltipState,
        tooltip = {
            PlainTooltip {
                Text("The developer will provideLocalUserSessionHandler you a secret key")
            }
        }
    ) {
        SignUpInTextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = { },
            label = "Institution secret key",
            trailingIcon = {
                IconButton(onClick = {
                    scope.launch {
                        if (tooltipState.isVisible) tooltipState.dismiss()
                        else tooltipState.show(MutatePriority.UserInput)
                    }

                }) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Info"
                    )
                }
            }
        )
    }
}






























