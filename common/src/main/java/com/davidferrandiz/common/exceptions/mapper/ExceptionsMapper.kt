package com.davidferrandiz.common.exceptions.mapper

import com.davidferrandiz.common.exceptions.ExceptionType
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

private const val CODE_400 = 400
private const val CODE_500 = 500

fun handleNetworkExceptions(ex: Exception): ExceptionType {
    return when (ex) {
        is IOException -> ExceptionType.NetworkException()
        is UnknownHostException -> ExceptionType.NetworkException()
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> ex.toGenericException()
    }
}

private fun apiErrorFromCodeException(code: Int): ExceptionType {
    return when (code) {
        in (CODE_400 until CODE_500) -> ExceptionType.ErrorException()
        else -> ExceptionType.NetworkException()
    }
}

private fun IOException.toNetworkException() = ExceptionType.NetworkException()
private fun UnknownHostException.toNetworkException() = ExceptionType.NetworkException()
private fun Exception.toGenericException() = ExceptionType.GenericException()
