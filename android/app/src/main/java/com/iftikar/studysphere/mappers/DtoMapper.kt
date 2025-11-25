package com.iftikar.studysphere.mappers

import com.iftikar.studysphere.data.dto.AdminResponseDto
import com.iftikar.studysphere.domain.model.Admin

fun AdminResponseDto.toAdmin(): Admin {
    return Admin(
        email = email,
        name = name,
        phone = phone.toString()
    )
}