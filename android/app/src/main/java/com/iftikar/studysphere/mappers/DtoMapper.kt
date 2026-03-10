package com.iftikar.studysphere.mappers

import com.iftikar.studysphere.data.dto.AdminDto
import com.iftikar.studysphere.domain.model.Admin

fun AdminDto.toAdmin(): Admin {
    return Admin(
        email = email,
        name = name,
        phone = phone.toString()
    )
}