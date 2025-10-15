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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iftikar.studysphere.presentation.components.SignUpInButtonComponent
import com.iftikar.studysphere.presentation.components.SignUpInTextFieldComponent
import com.iftikar.studysphere.presentation.components.TypewriterTextComponent
import com.iftikar.studysphere.presentation.navigation.Routes
import com.iftikar.studysphere.ui.theme.SignInUpBackground

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
            TypewriterTextComponent(
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
