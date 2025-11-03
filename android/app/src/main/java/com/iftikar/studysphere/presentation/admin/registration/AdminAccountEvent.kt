package com.iftikar.studysphere.presentation.admin.registration

import com.iftikar.studysphere.presentation.navigation.Routes

sealed interface AdminAccountEvent {
    data object Idle : AdminAccountEvent
    data object OnSuccessUnverified : AdminAccountEvent // unverified -> sent to verification screen
    data object OnLoginSuccessVerified : AdminAccountEvent //verified -> sent to dashboard or other screen
}