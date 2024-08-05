package com.example.domain.common.base

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
}


inline fun <T> Result<T>.onSuccess(
    block: (T) -> Unit
): Result<T> = if (this is Result.Success) also { block(data) } else this

inline fun <T> Result<T>.onError(
    block: (Throwable) -> Unit
): Result<T> = if (this is Result.Error) also { block(error) } else this

fun <T> Result<T>.asSuccessOrNull(): T? = (this as? Result.Success)?.data
