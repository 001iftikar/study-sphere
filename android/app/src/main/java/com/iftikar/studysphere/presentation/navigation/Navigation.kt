package com.iftikar.studysphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iftikar.studysphere.presentation.admin.NextFeatureScreen
import com.iftikar.studysphere.presentation.common.RoleChooseScreen
import com.iftikar.studysphere.presentation.admin.registration.AdminAccountViewModel
import com.iftikar.studysphere.presentation.admin.registration.AdminSignUpScreen
import com.iftikar.studysphere.presentation.admin.registration.AdminSignInScreen
import com.iftikar.studysphere.presentation.admin.registration.AdminVerificationScreen

@Composable
fun Navigation(
    adminAccountViewModel: AdminAccountViewModel,
    startDestination: Routes
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController, startDestination = startDestination
    ) {
        composable<Routes.RoleSelectionScreenRoute> {
            RoleChooseScreen(
                navHostController = navHostController
            )
        }

        composable<Routes.AdminLoginScreenRoute> {
            AdminSignInScreen(
                navHostController = navHostController,
                viewModel = adminAccountViewModel
            )
        }

        composable<Routes.AdminRegisterScreenRoute> {

            AdminSignUpScreen(
                navHostController = navHostController,
                viewModel = adminAccountViewModel
            )
        }

        composable<Routes.EmailVerificationScreenRoute> {

            AdminVerificationScreen(
                viewModel = adminAccountViewModel, navHostController = navHostController
            )
        }

        composable<Routes.NextFeatureScreenRoute> {
            NextFeatureScreen()
        }
    }
}