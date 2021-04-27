package com.highquality.base.exception

class UnAuthorizeException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()