package com.iftikar.studysphere.presentation.admin.registration

sealed interface AdminAccountAction {
    data class OnFullNameChange(val fullName: String) : AdminAccountAction
    data class OnUsernameChange(val username: String) : AdminAccountAction
    data class OnEmailChange(val email: String) : AdminAccountAction
    data class OnPasswordChange(val password: String) : AdminAccountAction
    data object OnPasswordVisibilityChange : AdminAccountAction
    data object OnSignUp : AdminAccountAction
    data object OnLogIn : AdminAccountAction
}