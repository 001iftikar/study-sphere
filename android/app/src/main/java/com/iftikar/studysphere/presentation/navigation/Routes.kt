package com.iftikar.studysphere.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    object RoleSelectionScreenRoute : Routes
    @Serializable
    object AdminLoginScreenRoute : Routes
    @Serializable
    object AdminRegisterScreenRoute : Routes
    @Serializable
    data class EmailVerificationScreenRoute(val name: String) : Routes
    @Serializable
    object NextFeatureScreenRoute : Routes
}