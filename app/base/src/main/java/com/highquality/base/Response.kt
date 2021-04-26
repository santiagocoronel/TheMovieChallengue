package com.highquality.base

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure<out R : Exception>(val error: R) : Response<Nothing>()
}
