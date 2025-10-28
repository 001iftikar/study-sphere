package com.iftikar.studysphere.utils

import java.time.Instant

fun String.toEpochMilli(): Long {
    return Instant.parse(this).toEpochMilli()
}