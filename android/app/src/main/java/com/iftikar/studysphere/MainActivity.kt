package com.iftikar.studysphere

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.iftikar.studysphere.presentation.navigation.Navigation
import com.iftikar.studysphere.presentation.admin.registration.AdminAccountViewModel
import com.iftikar.studysphere.ui.theme.StudySphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var viewModel: AdminAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            viewModel = hiltViewModel<AdminAccountViewModel>()
            StudySphereTheme {
                Navigation(viewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { uri ->
            val userId = uri.getQueryParameter("userId")
            val secret = uri.getQueryParameter("secret")
            if (userId != null && secret != null) {
                viewModel.verifyEmail(userId, secret)
            }
        }
    }
}
