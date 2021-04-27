package com.example.themoviechallenge.base.exception

class UnAuthorizeException(
    val statusCode: Int,
    val statusMessage: String
) : Exception()