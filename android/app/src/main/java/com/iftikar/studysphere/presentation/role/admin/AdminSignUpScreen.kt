package com.iftikar.studysphere.presentation.role.admin

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
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iftikar.studysphere.presentation.components.SignUpInButtonComponent
import com.iftikar.studysphere.presentation.components.SignUpInTextFieldComponent
import com.iftikar.studysphere.presentation.components.TypewriterTextComponent
import com.iftikar.studysphere.ui.theme.SignInUpBackground
import kotlinx.coroutines.launch

@Composable
fun AdminSignUpScreen(
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

            TypewriterTextComponent(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 144.dp, start = 40.dp),
                text = "Something something",
                text2 = "Anything Anything"
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {


                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
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
                        value = "",
                        onValueChange = {},
                        label = "Username",
                    )
                    Spacer(Modifier.height(8.dp))
                }

                item {
                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
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
                        value = "",
                        onValueChange = {},
                        label = "Phone number",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }

                item {
                    SignUpInTextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        label = "Password",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    // todo password hide unhide
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
                }

                item {
                    SignUpInButtonComponent(
                        title = "Sign up",
                        onClick = {
                            // todo register institution
                        }
                    )
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
                Text("The developer will provide you a secret key")
            }
        }
    ) {
        SignUpInTextFieldComponent(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {  },
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






























