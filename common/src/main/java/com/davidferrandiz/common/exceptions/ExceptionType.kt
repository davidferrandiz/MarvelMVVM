package com.davidferrandiz.common.exceptions

import java.lang.Exception

sealed class ExceptionType : Exception() {
    class NetworkException(val messageEx: String = "") : ExceptionType()
    class ErrorException(val messageEx: String = "") : ExceptionType()
    class GenericException(val messageEx: String = "") : ExceptionType()
}