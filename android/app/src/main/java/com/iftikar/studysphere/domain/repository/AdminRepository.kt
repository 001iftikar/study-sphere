package com.iftikar.studysphere.domain.repository

import com.iftikar.studysphere.data.dto.UserResponseDto
import com.iftikar.studysphere.domain.DataError
import com.iftikar.studysphere.domain.EmptyResult
import com.iftikar.studysphere.domain.Result
import io.appwrite.models.User

interface AdminRepository {
    suspend fun signUp(email: String, password: String, name: String): EmptyResult<DataError>
    suspend fun login(email: String, password: String): Result<User<UserResponseDto>, DataError>
    suspend fun sendEmailVerification(url: String): EmptyResult<DataError>
    suspend fun verifyEmail(userId: String, secret: String): EmptyResult<DataError>
    suspend fun checkAuthSession(): EmptyResult<DataError>
}