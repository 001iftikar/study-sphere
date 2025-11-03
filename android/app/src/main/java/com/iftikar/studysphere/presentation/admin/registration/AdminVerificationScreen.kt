package com.iftikar.studysphere.presentation.admin.registration

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.studysphere.presentation.navigation.Routes
import com.iftikar.studysphere.ui.theme.SignInUpBackground

@Composable
fun AdminVerificationScreen(
    navHostController: NavHostController,
    viewModel: AdminAccountViewModel,
    name: String,
    isVerified: Boolean
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(SignInUpBackground)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Log.d(
                    "Appwrite-Com-User",
                    "AdminVerificationScreen: ${state.fullName} & ${state.email}"
                )
                Text(
                    text = if (isVerified) "Hi ${name}, You are verified" else "Hi ${name}, Please verify your email",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (state.isButtonEnabled) {
                    Button(
                        onClick = if (!state.isVerified) {
                            { viewModel.sendEmailVerification() }
                        } else {
                            { navHostController.navigate(Routes.NextFeatureScreenRoute) }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = if (isVerified) "Continue" else "Verify",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                state.error?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}






























