package com.highquality.base.exception

data class NoInternetException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()