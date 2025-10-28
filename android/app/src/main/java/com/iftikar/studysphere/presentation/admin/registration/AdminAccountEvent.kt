package com.iftikar.studysphere.presentation.admin.registration

import com.iftikar.studysphere.presentation.navigation.Routes

sealed interface AdminAccountEvent {
    data object Idle : AdminAccountEvent
    data class OnSuccessUnverified(val route: Routes = Routes.EmailVerificationScreenRoute) : AdminAccountEvent // unverified -> sent to verification screen
    data class OnLoginSuccessVerified(val route: Routes = Routes.NextFeatureScreenRoute) : AdminAccountEvent //verified -> sent to dashboard or other screen
}