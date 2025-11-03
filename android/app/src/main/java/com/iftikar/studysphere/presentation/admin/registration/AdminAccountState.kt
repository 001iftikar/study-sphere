package com.iftikar.studysphere.presentation.admin.registration

data class AdminAccountState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isButtonEnabled: Boolean = true,
    val isVerified: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isSignupEnabled: Boolean = false
)

