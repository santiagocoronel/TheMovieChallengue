package com.highquality.base.exception

data class ServiceErrorException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()