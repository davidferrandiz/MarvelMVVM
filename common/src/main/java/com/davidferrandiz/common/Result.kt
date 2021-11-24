package com.davidferrandiz.common

import com.davidferrandiz.common.exceptions.ExceptionType

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val ex: ExceptionType) : Result<Nothing>()

    fun <R> fold(onSuccess: (data: T) -> R, onError: (error: ExceptionType) -> R): R {
        return if (this is Success) {
            onSuccess(this.data)
        } else {
            val exception = (this as Error).ex
            onError(exception)
        }
    }
}