package com.iftikar.studysphere.domain

import io.appwrite.exceptions.AppwriteException


typealias RootError = Error

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: RootError>(val error: E):
        Result<Nothing, E>
}

inline fun <T, E: RootError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

typealias EmptyResult<E> = Result<Unit, E>
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

fun AppwriteException.toDataError(): DataError.Remote {
    return when (this.type) {
        "user_already_exists" -> DataError.Remote.USER_EXISTS
        "general_argument_invalid" -> DataError.Remote.INVALID_EMAIL
        "user_invalid_credentials" -> DataError.Remote.AUTH_FAILED
        "user_password_mismatch" -> DataError.Remote.PASSWORD_MISMATCH
        "general_rate_limit_exceeded" -> DataError.Remote.TOO_MANY_REQUESTS
        "general_timeout" -> DataError.Remote.REQUEST_TIMEOUT
        else -> DataError.Remote.UNKNOWN
    }
}




































