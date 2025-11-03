package com.iftikar.studysphere

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.studysphere.presentation.admin.registration.AdminAccountViewModel
import com.iftikar.studysphere.presentation.navigation.Navigation
import com.iftikar.studysphere.presentation.navigation.Routes
import com.iftikar.studysphere.shared.SessionHandlingEvent
import com.iftikar.studysphere.shared.SessionHandlingViewModel
import com.iftikar.studysphere.ui.theme.StudySphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var sessionHandlingViewModel: SessionHandlingViewModel
    lateinit var adminAccountViewModel: AdminAccountViewModel
    lateinit var startDestination: Routes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            sessionHandlingViewModel = hiltViewModel<SessionHandlingViewModel>()
            adminAccountViewModel = hiltViewModel<AdminAccountViewModel>()
            val eventState by sessionHandlingViewModel.event.collectAsStateWithLifecycle()

            when(eventState) {
                SessionHandlingEvent.OnAuthFailed -> {
                    startDestination = Routes.AdminLoginScreenRoute
                }
                is SessionHandlingEvent.OnAuthSuccess -> {
                    startDestination = if ((eventState as SessionHandlingEvent.OnAuthSuccess).isVerified) {
                        Routes.NextFeatureScreenRoute
                    } else {
                        Routes.EmailVerificationScreenRoute
                    }
                }
                else -> Unit
            }

            StudySphereTheme {
                if(::startDestination.isInitialized) {
                    Navigation(adminAccountViewModel, startDestination)
                } else {
                    // todo -> implement a splash screen later
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { uri ->
            val userId = uri.getQueryParameter("userId")
            val secret = uri.getQueryParameter("secret")
            if (userId != null && secret != null) {
                adminAccountViewModel.verifyEmail(userId, secret)
            }
        }
    }
}
