package com.davidferrandiz.presentation.uidata.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: Pair<String, () -> Unit>? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: Pair<String, () -> Unit>? = null) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()
}