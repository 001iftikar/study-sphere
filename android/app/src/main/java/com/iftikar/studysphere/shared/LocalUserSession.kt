package com.iftikar.studysphere.shared

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class LocalUserSession(
    val isVerified: Boolean = false,
    val name: String = "",
    val expire: Long = Instant.now().toEpochMilli()
)
