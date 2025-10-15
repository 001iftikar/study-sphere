package com.iftikar.studysphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iftikar.studysphere.presentation.role.RoleChooseScreen
import com.iftikar.studysphere.presentation.role.admin.AdminSignUpScreen
import com.iftikar.studysphere.presentation.role.admin.AdminSignInScreen

@Composable
fun Navigation() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController, startDestination = Routes.RoleSelectionScreenRoute
    ) {
        composable<Routes.RoleSelectionScreenRoute> {
            RoleChooseScreen(
                navHostController = navHostController
            )
        }

        composable<Routes.AdminLoginScreenRoute> {
            AdminSignInScreen(
                navHostController = navHostController
            )
        }

        composable<Routes.AdminRegisterScreenRoute> {
            AdminSignUpScreen(
                navHostController = navHostController
            )
        }
    }
}